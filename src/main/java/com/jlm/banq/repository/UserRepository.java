package com.jlm.banq.repository;

import com.jlm.banq.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email) ;

    //select * from user where firstname = 'firstname'
    List<User> findAllByFirstname (String firstname) ;

    //select * from user where firstname like 'firstname'
    List<User> findAllByFirstnameContaining (String firstname) ;

    //select * from user where firstname ilike = 'firstname'
    List<User> findAllByFirstnameContainingIgnoreCase (String firstname) ;

    //select * from user u inner join account a on u.id = a.id_user and a.iban = 'DEE223'
    List<User> findAllByAccountIban(String iban) ;

    //select * from user where firstname = 'firstname' and email = 'email'
    User findByFirstnameContainingIgnoreCaseAndEmail(String firstname, String email) ;
    @Query("from User where firstname = :fn")
    List<User> searchByFirstname (@Param("fn") String firstname) ;

    @Query("from User where firstname = '%:firstname%'")
    List<User> searchByFirstnameContaining (@Param("firstname") String firstname) ;
    @Query("from User u inner join Account a on u.id = a.user.id where a.iban = :iban")
    List<User> searchByIban(String iban) ;
    @Query(value = "select * from User u inner join account a on u.id = a.id_user and a.iban = :iban", nativeQuery = true)
    List<User> searchByIbanNative(String iban) ;

}
