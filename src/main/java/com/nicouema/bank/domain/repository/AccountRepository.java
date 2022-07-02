package com.nicouema.bank.domain.repository;

import com.nicouema.bank.domain.model.Account;
import com.nicouema.bank.domain.model.AccountId;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AccountRepository extends PagingAndSortingRepository<Account, AccountId> {
}
