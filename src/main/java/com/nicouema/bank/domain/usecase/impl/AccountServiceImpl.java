package com.nicouema.bank.domain.usecase.impl;

import com.nicouema.bank.common.exception.NotFoundException;
import com.nicouema.bank.domain.model.*;
import com.nicouema.bank.domain.repository.AccountRepository;
import com.nicouema.bank.domain.repository.BranchRepository;
import com.nicouema.bank.domain.usecase.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final BranchRepository branchRepository;

    @Override
    @Transactional
    public Account getAccountById(AccountId id) {
        return accountRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    @Transactional
    public AccountId createAccount(Account account, User user, Long branchId) {

        account.setClient_(user.getClient());

        Branch branch = getBranchByIdIfExist(branchId);

        AccountId accountId = new AccountId();
        accountId.setBranch(branchId);

        account.setAccountId(accountId);
        account.setBranch(branch);
        account.setCurrentBalance(account.getInitialBalance());

        accountRepository.save(account);

        return account.getAccountId();
    }

    @Override
    @Transactional
    public BankStatementList getBankStatementsDesc(Account account, PageRequest pageRequest) {

        List<BankStatement> list = account.getStatements().stream()
                .sorted((a2, a1) -> a1.getAudit().getCreatedAt().compareTo(a2.getAudit().getCreatedAt()))
                .toList();

        Page<BankStatement> page = new PageImpl<>(list, pageRequest, list.size());

        return new BankStatementList(page.getContent(), pageRequest, page.getTotalElements());

    }

    @Override
    public AccountList getAllAccounts(PageRequest pageRequest) {
        Page<Account> page = accountRepository.findAll(pageRequest);

        return new AccountList(page.getContent(), pageRequest, page.getTotalElements());
    }

    @Override
    public Account getAccountFromUser(AccountId accountId, User user) {
        List<Account> accountList = user.getClient().getAccounts().stream().toList();
        for (Account account:accountList) {
            if (account.getAccountId().equals(accountId)){
                return account;
            }
        }
        throw new NotFoundException(accountId.getId());
    }

    private Branch getBranchByIdIfExist(Long branchId) {

        return branchRepository.findById(branchId).orElseThrow(() -> new NotFoundException(branchId));
    }
}
