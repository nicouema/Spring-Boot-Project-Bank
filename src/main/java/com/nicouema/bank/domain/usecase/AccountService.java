package com.nicouema.bank.domain.usecase;

import com.nicouema.bank.domain.model.*;
import org.springframework.data.domain.PageRequest;

public interface AccountService {

    Account getAccountById(AccountId id);

    AccountId createAccount(Account account, User user, Long branchId);

    BankStatementList getBankStatementsDesc(AccountId id, PageRequest pageRequest);

    AccountList getAllAccounts(PageRequest pageRequest);
}
