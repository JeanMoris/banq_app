package com.jlm.banq.services.impl;

import com.jlm.banq.dto.AddressDto;
import com.jlm.banq.dto.ContactDto;
import com.jlm.banq.handlers.IbanAlreadyExistsException;
import com.jlm.banq.models.Contact;
import com.jlm.banq.repository.ContactRepository;
import com.jlm.banq.services.ContactService;
import com.jlm.banq.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository repository ;

    private final ObjectsValidator<ContactDto> validator ;
    @Override
    public Integer save(ContactDto dto) {
        validator.validate(dto);
        if (repository.existsByIban(dto.getIban())) {
            throw new IbanAlreadyExistsException("L'IBAN " + dto.getIban() + " existe déjà.");
        }
        Contact contact = ContactDto.toEntity(dto);
        return repository.save(contact).getId();
    }

    @Override
    public List<ContactDto> findAll() {
        return repository.findAll()
                .stream()
                .map(ContactDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ContactDto findById(Integer id) {
        return repository.findById(id)
                .map(ContactDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No contact was found with the ID :" + id));
    }

    @Override
    public void delete(Integer id) {
        // todo check delete
        repository.deleteById(id);
    }

    @Override
    public List<ContactDto> findAllByUserId(Integer userId) {
        return repository.findAllByUserId(userId)
                .stream()
                .map(ContactDto::fromEntity)
                .collect(Collectors.toList());
    }
}
