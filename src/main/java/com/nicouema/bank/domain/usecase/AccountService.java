package com.nicouema.bank.domain.usecase;

import com.nicouema.bank.domain.model.*;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface AccountService {

    Account getAccountById(AccountId id);

    AccountId createAccount(Account account, User user, Long branchId);

    BankStatementList getBankStatementsDesc(Account account, PageRequest pageRequest);

    AccountList getAccounts(PageRequest pageRequest);

    Account getAccountFromUser(AccountId accountId, User user);

    void deleteAccountById(AccountId accountId);

    List<Account> getAllAccounts();
}
