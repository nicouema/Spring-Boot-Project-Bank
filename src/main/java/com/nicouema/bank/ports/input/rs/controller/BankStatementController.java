package com.nicouema.bank.ports.input.rs.controller;

import com.nicouema.bank.domain.model.AccountId;
import com.nicouema.bank.domain.model.BankStatement;
import com.nicouema.bank.domain.model.BankStatementList;
import com.nicouema.bank.domain.usecase.BankStatementService;
import com.nicouema.bank.ports.input.rs.api.BankStatementApi;
import com.nicouema.bank.ports.input.rs.mapper.BankStatementControllerMapper;
import com.nicouema.bank.ports.input.rs.request.BankStatementRequest;
import com.nicouema.bank.ports.input.rs.response.BankStatementListResponse;
import com.nicouema.bank.ports.input.rs.response.BankStatementResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

import static com.nicouema.bank.ports.input.rs.api.ApiConstants.BANK_STATEMENT_URI;

@RestController
@RequestMapping(BANK_STATEMENT_URI)
@RequiredArgsConstructor
public class BankStatementController implements BankStatementApi {

    private final BankStatementService service;
    private final BankStatementControllerMapper mapper;



    @Override
    @PostMapping
    public ResponseEntity<Void> createBankStatement(@RequestBody BankStatementRequest createBankStatementRequest) {
        BankStatement bankStatement = mapper.bankStatementRequestToBankStatement(createBankStatementRequest);
        final Long id = service.createBankStatement(bankStatement);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}").buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    @PatchMapping
    public ResponseEntity<BankStatementResponse> updateBankStatement(Long id, BankStatementRequest updateBankStatementRequest) {
        BankStatement bankStatement = mapper.bankStatementRequestToBankStatement(updateBankStatementRequest);

        service.updateBankStatement(id, bankStatement);

        BankStatementResponse response = mapper.bankStatementToBankStatementResponse(bankStatement);

        return ResponseEntity.ok(response);
    }

    @Override
    public BankStatementResponse getBankStatementById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<BankStatementListResponse> getAllBankStatements(Optional<Integer> page, Optional<Integer> size) {
        return null;
    }

    @Override
    public ResponseEntity<BankStatementList> getBankStatementsByMovementType(Long movementTypeId, Optional<Integer> page, Optional<Integer> size) {
        return null;
    }

    @Override
    public ResponseEntity<BankStatementList> getBankStatementsByAccount(AccountId accountId, Optional<Integer> page, Optional<Integer> size) {
        return null;
    }
}
