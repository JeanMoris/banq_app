package com.jlm.banq.services.impl;


import com.jlm.banq.dto.TransactionDto;
import com.jlm.banq.models.Transaction;
import com.jlm.banq.models.TransactionType;
import com.jlm.banq.repository.TransactionRepository;
import com.jlm.banq.services.TransactionService;
import com.jlm.banq.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ali Bouali
 */

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

  private final TransactionRepository repository;
  private final ObjectsValidator<TransactionDto> validator;

  @Override
  public Integer save(TransactionDto dto) {
    validator.validate(dto);
    Transaction transaction = TransactionDto.toEntity(dto);
    BigDecimal transactionMultiplier = BigDecimal.valueOf(getTransactionMultiplier(transaction.getType()));
    BigDecimal amount = transaction.getAmount().multiply(transactionMultiplier);
    transaction.setAmount(amount);
    return repository.save(transaction).getId();
  }

  @Override
  public List<TransactionDto> findAll() {
    return repository.findAll()
        .stream()
        .map(TransactionDto::fromEntity)
        .collect(Collectors.toList());
  }

  @Override
  public TransactionDto findById(Integer id) {
    return repository.findById(id)
        .map(TransactionDto::fromEntity)
        .orElseThrow(() -> new EntityNotFoundException("No transaction was found with the ID: " + id));
  }

  @Override
  public void delete(Integer id) {
    // todo check delete
    repository.deleteById(id);
  }

  private int getTransactionMultiplier(TransactionType type) {
    return TransactionType.TRANSFERT == type ? -1 : 1;
  }


  @Override
  public List<TransactionDto> findAllByUserId(Integer userId) {
    return repository.findAllByUserId(userId)
        .stream()
        .map(TransactionDto::fromEntity)
        .collect(Collectors.toList());
  }
}
