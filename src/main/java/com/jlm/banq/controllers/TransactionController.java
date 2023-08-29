package com.jlm.banq.controllers;

import com.jlm.banq.dto.ContactDto;
import com.jlm.banq.dto.TransactionDto;
import com.jlm.banq.services.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
@Tag(name = "transaction")
public class TransactionController {

    public final TransactionService service ;

    @PostMapping("/")
    public ResponseEntity<Integer> save(@RequestBody TransactionDto transactionDto){
        return ResponseEntity.ok(service.save(transactionDto)) ;
    }
    @GetMapping("/")
    public ResponseEntity<List<TransactionDto>> findAll(){
        return ResponseEntity.ok(service.findAll()) ;
    }
    @GetMapping("/{transaction-id}")
    public ResponseEntity<TransactionDto> findById(@PathVariable("transaction-id") Integer transactionId){
        return ResponseEntity.ok(service.findById(transactionId));
    }


    @GetMapping("/users/{user-id}")
    public ResponseEntity<List<TransactionDto>> findAllByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(service.findAllByUserId(userId));
    }

    @DeleteMapping("/{transaction-id}")
    public ResponseEntity<Void> delete(@PathVariable("transaction-id") Integer transactionId){
        service.delete(transactionId);
        return ResponseEntity.accepted().build() ;
    }
}
