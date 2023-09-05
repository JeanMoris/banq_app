package com.jlm.banq.repository;

import com.jlm.banq.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Ali Bouali
 */
public interface ContactRepository extends JpaRepository<Contact, Integer> {

  List<Contact> findAllByUserId(Integer userId);

  boolean existsByIban(String iban) ;
}
