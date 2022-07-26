package com.nicouema.bank.ports.input.rs.controller;

import com.nicouema.bank.domain.model.*;
import com.nicouema.bank.domain.usecase.AccountService;
import com.nicouema.bank.ports.input.rs.api.AccountApi;
import com.nicouema.bank.ports.input.rs.api.ApiConstants;
import com.nicouema.bank.ports.input.rs.mapper.AccountControllerMapper;
import com.nicouema.bank.ports.input.rs.mapper.BankStatementControllerMapper;
import com.nicouema.bank.ports.input.rs.request.CreateAccountRequest;
import com.nicouema.bank.ports.input.rs.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.nicouema.bank.ports.input.rs.api.ApiConstants.*;
import static com.nicouema.bank.ports.input.rs.api.ApiConstants.DEFAULT_PAGE_SIZE;

@RestController
@RequestMapping(ACCOUNT_URI)
@RequiredArgsConstructor
public class AccountController implements AccountApi {

    private final AccountService accountService;

    private final AccountControllerMapper mapper;

    private final BankStatementControllerMapper statementMapper;

    @Override
    @PostMapping
    public ResponseEntity<Void> createAccount(@RequestBody CreateAccountRequest createAccountRequest,
                                              @AuthenticationPrincipal User user) {

        Account account = mapper.createAccountRequestToAccount(createAccountRequest);

        AccountId id = accountService.createAccount(account, user, createAccountRequest.getBranchId());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}").buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    @GetMapping("/{branch_id}/{account_id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long account_id,
                                                          @PathVariable Long branch_id) {
        AccountId accountId = new AccountId();
        accountId.setBranch(branch_id);
        accountId.setId(account_id);

        Account account = accountService.getAccountById(accountId);

        AccountResponse response = mapper.accountToAccountResponse(account);

        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/{branch_id}/{account_id}/statements")
    public ResponseEntity<BankStatementListResponse> getBankStatementDesc(@PathVariable Long account_id,
                                                                          @PathVariable Long branch_id,
                                                                          @RequestParam Optional<Integer> page,
                                                                          @RequestParam Optional<Integer> size) {

        AccountId id = new AccountId();
        id.setBranch(branch_id);
        id.setId(account_id);

        final int pageNumber = page.filter(p -> p > 0).orElse(ApiConstants.DEFAULT_PAGE);
        final int pageSize = page.filter(s -> s > 0).orElse(ApiConstants.DEFAULT_PAGE_SIZE);

        BankStatementList list = accountService.getBankStatementsDesc(id, PageRequest.of(pageNumber, pageSize));

        BankStatementListResponse response;
        {
            response = new BankStatementListResponse();

            List<BankStatementResponse> content = statementMapper
                    .bankStatementListToBankStatementListResponse(list.getContent());
            response.setContent(content);

            final int nextPage = list.getPageable().next().getPageNumber();
            response.setNextUri(uriByPageAsString.apply(nextPage));

            final int previousPage = list.getPageable().previousOrFirst().getPageNumber();
            response.setPreviousUri(uriByPageAsString.apply(previousPage));

            response.setTotalPages(list.getTotalPages());
            response.setTotalElements(list.getTotalElements());
        }
        return ResponseEntity.ok().body(response);
    }

    @Override
    @GetMapping
    public ResponseEntity<AccountListResponse> getAllAccounts(@RequestParam Optional<Integer> page,
                                                              @RequestParam Optional<Integer> size) {
        final int pageNumber = page.filter( p -> p > 0 ).orElse(DEFAULT_PAGE);
        final int pageSize = page.filter( s -> s > 0 ).orElse(DEFAULT_PAGE_SIZE);

        AccountList list = accountService.getAllAccounts(PageRequest.of(pageNumber, pageSize));

        AccountListResponse response;
        {
            response = new AccountListResponse();

            List<AccountResponse> content = mapper.accountListToAccountListResponse(list.getContent());
            response.setContent(content);

            final int nextPage = list.getPageable().next().getPageNumber();
            response.setNextUri(uriByPageAsString.apply(nextPage));

            final int previousPage = list.getPageable().previousOrFirst().getPageNumber();
            response.setPreviousUri(uriByPageAsString.apply(previousPage));

            response.setTotalPages(list.getTotalPages());
            response.setTotalElements(list.getTotalElements());
        }
        return ResponseEntity.ok().body(response);
    }
}
