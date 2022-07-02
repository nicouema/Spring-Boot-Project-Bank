package com.nicouema.bank.domain.repository;

import com.nicouema.bank.domain.model.BankStatement;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BankStatementRepository extends PagingAndSortingRepository<BankStatement, Long> {
}
