package com.nicouema.bank.domain.usecase.impl;

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
import org.springframework.data.util.Streamable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

        Client accountOwner = account.getClient_();

        if (verifyClient(accountOwner, client)){
            bankStatement.setAccount(account);
            bankStatement.createStrategyInterface();
            account.addStatement(bankStatement);
        }
        else{
            throw new NotFoundException(accountId.getId());
        }

        accountRepository.save(account);

        return bankStatementRepository.save(bankStatement).getId();
    }

    private Account getAccountIfExist(AccountId accountId) {

        return accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException(accountId.getId()));

    }

    @Override
    @Transactional
    public BankStatement updateBankStatement(Long id, BankStatement bankStatement, Client client) {

        BankStatement bankStatementToUpdate = bankStatementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
        Client statementOwner = bankStatementToUpdate.getAccount().getClient_();
        if (verifyClient(client, statementOwner)){
            if (bankStatement.getAmount() != null) {
                bankStatementToUpdate.setAmount(bankStatement.getAmount());
            }
            if (bankStatement.getMovementType().getId() != null) {
                MovementType movementType = movementTypeRepository.findById(bankStatement.getMovementType().getId())
                        .orElseThrow(() -> new NotFoundException(bankStatement.getMovementType().getId()));
                bankStatementToUpdate.setMovementType(movementType);
            }

            Account account = bankStatementToUpdate.getAccount();
            bankStatementToUpdate.createStrategyInterface();
            account.updateCurrentBalance(bankStatementToUpdate);

            accountRepository.save(account);
            bankStatementRepository.save(bankStatementToUpdate);
            return bankStatementToUpdate;
        }
        else {
            throw new NotFoundException(id);
        }

    }

    @Override
    @Transactional
    public BankStatement getBankStatementById(Long id, Client client) {
        BankStatement statement = bankStatementRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        Client resourceOwner = statement.getAccount().getClient_();
        if (verifyClient(resourceOwner, client)){
            return statement;
        }
        else {
            throw new NotFoundException(id);
        }
    }

    @Override
    @Transactional
    public BankStatementList getBankStatements(PageRequest pageRequest) {
        Page<BankStatement> page = bankStatementRepository.findAll(pageRequest);
        return new BankStatementList(page.getContent(), pageRequest, page.getTotalElements());
    }

    @Override
    @Transactional
    public BankStatementList getBankStatementByMovementType(Long id, PageRequest pageRequest) {
        List<BankStatement> list = bankStatementRepository.findAll(pageRequest).filter(filterByMovementType(id))
                .stream().toList();
        Page<BankStatement> page = new PageImpl<>(list, pageRequest, list.size());
        return new BankStatementList(page.getContent(), pageRequest, page.getTotalElements());
    }

    @Override
    @Transactional
    public List<BankStatement> getAllBankStatements() {
        Iterable<BankStatement> iterator = bankStatementRepository.findAll();
        return Streamable.of(iterator).toList();
    }


    public Predicate<BankStatement> filterByMovementType(Long movementTypeId) {
        return (BankStatement bankStatement) -> {
            return Objects.equals(bankStatement.getMovementType().getId(), movementTypeId);
        };
    }

    public Boolean verifyClient(Client clientInSession, Client resourceOwner) {
        return Objects.equals(clientInSession, resourceOwner);
    }
}
