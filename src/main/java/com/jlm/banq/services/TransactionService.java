package com.jlm.banq.services;

import com.jlm.banq.dto.TransactionDto;
import com.jlm.banq.models.Transaction;

import java.util.List;

public interface TransactionService extends AbstractService<TransactionDto>{

  List<TransactionDto> findAllByUserId(Integer userId);
}
