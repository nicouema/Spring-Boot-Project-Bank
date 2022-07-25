package com.nicouema.bank.domain.usecase;

import com.nicouema.bank.domain.model.AccountId;
import com.nicouema.bank.domain.model.BankStatement;
import com.nicouema.bank.domain.model.BankStatementList;
import com.nicouema.bank.domain.model.Client;
import org.springframework.data.domain.PageRequest;

public interface BankStatementService {

    Long createBankStatement(BankStatement bankStatement, Long movementTypeId, AccountId accountId, Client client);

    BankStatement updateBankStatement(Long id, BankStatement bankStatement);

    BankStatement getBankStatementById(Long id);

    BankStatementList getAllBankStatements(PageRequest pageRequest);

    BankStatementList getBankStatementByMovementType(Long id, PageRequest pageRequest);

    BankStatementList getBankStatementByAccount(AccountId id, PageRequest pageRequest);
}
