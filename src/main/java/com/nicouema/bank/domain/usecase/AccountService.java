package com.nicouema.bank.domain.usecase;

import com.nicouema.bank.domain.model.Account;
import com.nicouema.bank.domain.model.AccountId;
import com.nicouema.bank.domain.model.BankStatementList;
import com.nicouema.bank.domain.model.User;
import org.springframework.data.domain.PageRequest;

public interface AccountService {

    Account getAccountById(AccountId id);

    AccountId createAccount(Account account, User user, Long branchId);

    BankStatementList getBankStatementsDesc(AccountId id, PageRequest pageRequest);
}
