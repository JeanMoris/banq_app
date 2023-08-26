package com.jlm.banq.repository;

import com.jlm.banq.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ali Bouali
 */
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
