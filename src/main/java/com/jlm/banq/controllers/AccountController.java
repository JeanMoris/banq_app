package com.jlm.banq.controllers;

import com.jlm.banq.dto.AccountDto;
import com.jlm.banq.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService service ;
    @PostMapping("/")
    public ResponseEntity<Integer> save(@RequestBody AccountDto accountDto){
        return ResponseEntity.ok(service.save(accountDto)) ;
    }
    @GetMapping("/")
    public ResponseEntity<List<AccountDto>> findAll(){
        return ResponseEntity.ok(service.findAll()) ;
    }
    @GetMapping("/{account-id}")
    public ResponseEntity<AccountDto> findById(@PathVariable("") Integer accountId){
        return ResponseEntity.ok(service.findById(accountId));
    }
    @DeleteMapping("/{account-id}")
    public ResponseEntity<Void> delete(@PathVariable Integer accountId){
        service.delete(accountId);
        return ResponseEntity.accepted().build() ;
    }
}
