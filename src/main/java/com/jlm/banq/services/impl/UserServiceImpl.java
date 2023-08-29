package com.jlm.banq.services.impl;

import com.jlm.banq.config.JwtUtils;
import com.jlm.banq.dto.*;
import com.jlm.banq.exceptions.OperationNonPermittedException;
import com.jlm.banq.models.Role;
import com.jlm.banq.models.User;
import com.jlm.banq.repository.RoleRepository;
import com.jlm.banq.repository.UserRepository;
import com.jlm.banq.services.AccountService;
import com.jlm.banq.services.UserService;
import com.jlm.banq.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String ROLE_USER = "ROLE_USER";
    private final UserRepository repository;
    private final AccountService accountService;
    private final ObjectsValidator<UserDto> validator;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authManager;
    private final RoleRepository roleRepository;
    @Override
    public Integer save(UserDto dto) {
        validator.validate(dto);
        User user = UserDto.toEntity(dto);
        return repository.save(user).getId();
    }

    @Override
    public List<UserDto> findAll() {
        return repository.findAll()
                .stream()
                .map(UserDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(Integer id) {
        return repository.findById(id)
                .map(UserDto::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException("No user was found with the provided ID"));
    }

    @Override
    public void delete(Integer id) {
    repository.deleteById(id);
    }

    @Override
    //@Transactional
    public Integer validateAccount(Integer userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("No user was found for user account validation"));

        if (user.isActive()) {
            throw new OperationNonPermittedException(
                    "The selected user is already active",
                    "Validate account",
                    "Account service",
                    "Account validation"
            );
        }

        if (user.getAccount() == null) {
            // Create a new account and associate it with the user
            AccountDto account = AccountDto.builder()
                    .user(UserDto.fromEntity(user))
                    .build();
            accountService.save(account);
        } else {
            throw new OperationNonPermittedException(
                    "The selected user already has an account",
                    "Validate account",
                    "Account service",
                    "Account validation"
            );
        }

        // Activate the user
        user.setActive(true);
        repository.save(user);
        return user.getId();
    }


    @Override
    public Integer invalidateAccount(Integer id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No user was found for user account validation"));

        // Désactiver l'utilisateur
        user.setActive(false);
        repository.save(user) ;
        return user.getId();
    }

    @Override
    @Transactional
    public AuthenticationResponse register(UserDto dto) {
        validator.validate(dto);
        User user = UserDto.toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(
                findOrCreateRole(ROLE_USER)
        );
        var savedUser = repository.save(user);
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", savedUser.getId());
        claims.put("fullName", savedUser.getFirstname() + " " + savedUser.getLastname());
        String token = jwtUtils.generateToken(savedUser, claims);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        final User user = repository.findByEmail(request.getEmail()).get();
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("fullName", user.getFirstname() + " " + user.getLastname());
        final String token = jwtUtils.generateToken(user, claims);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public Integer update(LightUserDto userDto) {
        User user = LightUserDto.toEntity(userDto);
        return repository.save(user).getId();
    }

    private Role findOrCreateRole(String roleName) {
        Role role = roleRepository.findByName(roleName)
                .orElse(null);
        if (role == null) {
            return roleRepository.save(
                    Role.builder()
                            .name(roleName)
                            .build()
            );
        }
        return role;
    }
}
