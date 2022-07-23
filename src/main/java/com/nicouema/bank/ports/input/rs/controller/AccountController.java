package com.nicouema.bank.ports.input.rs.controller;

import com.nicouema.bank.domain.model.Account;
import com.nicouema.bank.domain.model.AccountId;
import com.nicouema.bank.domain.model.User;
import com.nicouema.bank.domain.usecase.AccountService;
import com.nicouema.bank.ports.input.rs.api.AccountApi;
import com.nicouema.bank.ports.input.rs.mapper.AccountControllerMapper;
import com.nicouema.bank.ports.input.rs.request.CreateAccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.nicouema.bank.ports.input.rs.api.ApiConstants.ACCOUNT_URI;

@RestController
@RequestMapping(ACCOUNT_URI)
@RequiredArgsConstructor
public class AccountController implements AccountApi {

    private final AccountService accountService;

    private final AccountControllerMapper mapper;

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
}
