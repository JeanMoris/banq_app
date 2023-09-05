package com.jlm.banq.dto;



import com.jlm.banq.models.Contact;
import com.jlm.banq.models.User;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "iban", name = "uk_iban"))
public class ContactDto {

    private Integer id;
    @NotNull(message = "Le prenom ne doit pas être vide")
    @NotEmpty(message = "Le prenom ne doit pas être vide")
    @NotBlank(message = "Le prenom ne doit pas être vide")
    private String firstname;
    @NotNull(message = "Le nom ne doit pas être vide")
    @NotEmpty(message = "Le nom ne doit pas être vide")
    @NotBlank(message = "Le nom ne doit pas être vide")
    private String lastname;
    @NotNull(message = "L'email ne doit pas être vide")
    @NotEmpty(message = "L'email ne doit pas être vide")
    @NotBlank(message = "L'email ne doit pas être vide")
    @Email(message = "L'email n'est pas conforme")
    private String email;
    @Pattern(regexp = "^[A-Z]{2}\\d{2}\\s\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{2}$",
            message = "L'IBAN n'est pas valide")
    @NotNull(message = "L'IBAN ne doit pas être vide")
    @NotEmpty(message = "L'IBAN ne doit pas être vide")
    @NotBlank(message = "L'IBAN ne doit pas être vide")
    @Column(unique = true)
    private String iban;

    private Integer userId;

    public static ContactDto fromEntity(Contact contact) {
        return ContactDto.builder()
                .id(contact.getId())
                .firstname(contact.getFirstname())
                .lastname(contact.getLastname())
                .email(contact.getEmail())
                .iban(contact.getIban())
                .userId(contact.getUser().getId())
                .build();

    }

    public static Contact toEntity(ContactDto contact) {
        return Contact.builder()
                .id(contact.getId())
                .firstname(contact.getFirstname())
                .lastname(contact.getLastname())
                .email(contact.getEmail())
                .iban(contact.getIban())
                .user(
                        User.builder()
                                .id(contact.getUserId())
                                .build()
                )
                .build();
    }

}
