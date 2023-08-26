package com.jlm.banq.services;


import com.jlm.banq.dto.TransactionSumDetails;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Ali Bouali
 */
public interface StatisticsService {

  List<TransactionSumDetails> findSumTransactionsByDate(LocalDate startDate, LocalDate endDate, Integer userId);

  BigDecimal getAccountBalance(Integer userId);

  BigDecimal highestTransfert(Integer userId);

  BigDecimal highestDeposit(Integer userId);

}
