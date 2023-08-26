package com.jlm.banq.services.impl;

import com.jlm.banq.dto.AccountDto;
import com.jlm.banq.exceptions.OperationNonPermittedException;
import com.jlm.banq.models.Account;
import com.jlm.banq.models.User;
import com.jlm.banq.repository.AccountRepository;
import com.jlm.banq.repository.UserRepository;
import com.jlm.banq.services.AccountService;
import com.jlm.banq.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository ;

    private final UserRepository userRepository ;
    private final ObjectsValidator<AccountDto> validator ;
    @Override
    public Integer save(AccountDto dto) {
        validator.validate(dto);
        Account account = AccountDto.toEntity(dto);
        boolean userHasAlreadyAnAccount = repository.findByUserId(account.getUser().getId()).isPresent();
        if (userHasAlreadyAnAccount && account.getUser().isActive()) {
            throw new OperationNonPermittedException(
                    "the selected user has already an active account",
                    "Create account",
                    "Account service",
                    "Account creation"
            );
        }
        // generate random IBAN when creating new account else do not update the IBAN
        if (dto.getId() == null) {
            account.setIban(generateRandomIban());
        }
        return repository.save(account).getId();
    }

    @Override
    public List<AccountDto> findAll() {
        return repository.findAll()
                .stream()
                .map(AccountDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto findById(Integer id) {
        return repository.findById(id)
                .map(AccountDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No account was found with the ID : " + id));
    }

    @Override
    public void delete(Integer id) {
        // todo check delete account
        repository.deleteById(id);
    }

    private String generateRandomIban(){
        String iban = Iban.random(CountryCode.DE).toFormattedString() ;
        boolean ibanExists = repository.findByIban(iban).isPresent() ;

        if (ibanExists){
            generateRandomIban();
        }
        return iban ;
    }
}
