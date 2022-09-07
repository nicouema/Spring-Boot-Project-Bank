package com.nicouema.bank.domain.usecase;

import com.nicouema.bank.domain.model.*;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface BankStatementService {

    Long createBankStatement(BankStatement bankStatement, Long movementTypeId, AccountId accountId, Client client);

    BankStatement updateBankStatement(Long id, BankStatement bankStatement, Client client);

    BankStatement getBankStatementById(Long id, Client client);

    BankStatementList getBankStatements(PageRequest pageRequest);

    BankStatementList getBankStatementByMovementType(Long id, PageRequest pageRequest);

    List<BankStatement> getAllBankStatements();

}
