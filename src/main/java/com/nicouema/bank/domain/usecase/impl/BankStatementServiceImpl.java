package com.nicouema.bank.domain.usecase.impl;

import com.nicouema.bank.common.exception.ConflictException;
import com.nicouema.bank.common.exception.NotFoundException;
import com.nicouema.bank.domain.model.*;
import com.nicouema.bank.domain.repository.AccountRepository;
import com.nicouema.bank.domain.repository.BankStatementRepository;
import com.nicouema.bank.domain.repository.MovementTypeRepository;
import com.nicouema.bank.domain.usecase.BankStatementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class BankStatementServiceImpl implements BankStatementService {

    private final BankStatementRepository bankStatementRepository;

    private final AccountRepository accountRepository;

    private final MovementTypeRepository movementTypeRepository;


    @Override
    @Transactional
    public Long createBankStatement(BankStatement bankStatement, Long movementTypeId, AccountId accountId, Client client) {

        Account account = getAccountIfExist(accountId);

        MovementType movementType = movementTypeRepository.findById(movementTypeId)
                .orElseThrow(() -> new NotFoundException(movementTypeId));

        bankStatement.setMovementType(movementType);

        if (account.getClient_().equals(client)){
            bankStatement.setAccount(account);
            account = account.updateCurrentBalance(bankStatement);
        }
        else{
            throw new ConflictException("current client doesn't own this account");
        }

        accountRepository.save(account);

        return bankStatementRepository.save(bankStatement).getId();
    }

    private Account getAccountIfExist(AccountId accountId) {

        return accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(accountId.getId()));

    }

    @Override
    @Transactional
    public BankStatement updateBankStatement(Long id, BankStatement bankStatement) {

        BankStatement bankStatementToUpdate = bankStatementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
        MovementType movementType = movementTypeRepository.findById(bankStatement.getMovementType().getId())
                        .orElseThrow(() -> new NotFoundException(bankStatement.getMovementType().getId()));

        if (bankStatement.getAmount() != null) {
            bankStatementToUpdate.setAmount(bankStatement.getAmount());
        }
        if (bankStatement.getMovementType().getId() != null) {
            bankStatementToUpdate.setMovementType(movementType);
        }

        bankStatementRepository.save(bankStatementToUpdate);
        return bankStatementToUpdate;
    }

    @Override
    @Transactional
    public BankStatement getBankStatementById(Long id) {
        return bankStatementRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    @Transactional
    public BankStatementList getAllBankStatements(PageRequest pageRequest) {
        Page<BankStatement> page = bankStatementRepository.findAll(pageRequest);
        return new BankStatementList(page.getContent(), pageRequest, page.getTotalElements());
    }

    @Override
    @Transactional
    public BankStatementList getBankStatementByMovementType(Long id, PageRequest pageRequest) {
        List<BankStatement> list = bankStatementRepository.findAll(pageRequest).filter(filterByMovementType(id))
                .stream().toList();
        Page<BankStatement> page = new PageImpl<>(list);
        return new BankStatementList(page.getContent(), pageRequest, page.getTotalElements());
    }


    public Predicate<BankStatement> filterByMovementType(Long movementTypeId) {
        return (BankStatement bankStatement) -> {
            return Objects.equals(bankStatement.getMovementType().getId(), movementTypeId);
        };
    }
}
