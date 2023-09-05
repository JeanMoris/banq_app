package com.jlm.banq.dto;

import com.jlm.banq.models.User;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserDto {

    private Integer id ;
    @NotNull(message = "Le prenom ne doit pas être vide")
    @NotEmpty(message = "Le prenom ne doit pas être vide")
    @NotBlank(message = "Le prenom ne doit pas être vide")
    private String firstname ;
    @NotNull(message = "Le nom ne doit pas être vide")
    @NotEmpty(message = "Le nom ne doit pas être vide")
    @NotBlank(message = "Le nom ne doit pas être vide")
    private  String lastname ;
    @NotNull(message = "L'email ne doit pas être vide")
    @NotEmpty(message = "L'email ne doit pas être vide")
    @NotBlank(message = "L'email ne doit pas être vide")
    @Email(message = "L'email n'est pas conforme")
    private String email ;
    @NotNull(message = "Le mot de passe ne doit pas être vide")
    @NotEmpty(message = "Le mot de passe ne doit pas être vide")
    @NotBlank(message = "Le mot de passe ne doit pas être vide")
    @Size(min = 8, max = 16, message = "Le mot de passe doit être entre 8 et 16 caracteres")
    private String password ;

    private String iban ;

    private boolean active ;
    

    public static UserDto fromEntity(User user) {
        //null check
        return UserDto.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .password(user.getPassword())
                .iban(user.getAccount() == null ? "" : user.getAccount().getIban())
                .active(user.isActive())
                .build() ;
    }

    public static User toEntity(UserDto user) {
        //null check
        return User.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .password(user.getPassword())
                .build() ;
    }
}
