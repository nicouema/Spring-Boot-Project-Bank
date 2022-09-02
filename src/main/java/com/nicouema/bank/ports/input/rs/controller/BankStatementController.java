package com.nicouema.bank.ports.input.rs.controller;

import com.nicouema.bank.domain.model.*;
import com.nicouema.bank.domain.usecase.BankStatementService;
import com.nicouema.bank.ports.input.rs.api.ApiConstants;
import com.nicouema.bank.ports.input.rs.api.BankStatementApi;
import com.nicouema.bank.ports.input.rs.mapper.BankStatementControllerMapper;
import com.nicouema.bank.ports.input.rs.request.BankStatementRequest;
import com.nicouema.bank.ports.input.rs.response.BankStatementListResponse;
import com.nicouema.bank.ports.input.rs.response.BankStatementResponse;
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

@RestController
@RequestMapping(BANK_STATEMENT_URI)
@RequiredArgsConstructor
public class BankStatementController implements BankStatementApi {

    private final BankStatementService service;
    private final BankStatementControllerMapper mapper;



    @Override
    @PostMapping("/{branch_id}/{account_id}")
    public ResponseEntity<Void> createBankStatement(@RequestBody BankStatementRequest createBankStatementRequest,
                                                    @PathVariable Long branch_id, @PathVariable Long account_id,
                                                    @AuthenticationPrincipal User user) {
        BankStatement bankStatement = mapper.bankStatementRequestToBankStatement(createBankStatementRequest);

        AccountId accountId = new AccountId();
        accountId.setId(account_id);
        accountId.setBranch(branch_id);

        Client client = user.getClient();

        final Long id = service.createBankStatement(bankStatement,
                createBankStatementRequest.getMovementType(),
                accountId,
                client);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}").buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    @PatchMapping("{id}")
    public ResponseEntity<BankStatementResponse> updateBankStatement(@PathVariable Long id,
                                                                     @RequestBody BankStatementRequest updateBankStatementRequest) {
        BankStatement bankStatement = mapper.bankStatementRequestToBankStatement(updateBankStatementRequest);


        bankStatement = service.updateBankStatement(id, bankStatement);

        BankStatementResponse response = mapper.bankStatementToBankStatementResponse(bankStatement);

        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("{id}")
    public ResponseEntity<BankStatementResponse> getBankStatementById(@PathVariable Long id) {
        BankStatement bankStatement = service.getBankStatementById(id);
        BankStatementResponse response = mapper.bankStatementToBankStatementResponse(bankStatement);
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping
    public ResponseEntity<BankStatementListResponse> getAllBankStatements(@RequestParam Optional<Integer> page,
                                                                          @RequestParam Optional<Integer> size) {
        final int pageNumber = page.filter(p -> p > 0).orElse(DEFAULT_PAGE);
        final int pageSize = size.filter(s -> s > 0).orElse(DEFAULT_PAGE_SIZE);

        BankStatementList list = service.getBankStatements(PageRequest.of(pageNumber, pageSize));

        BankStatementListResponse response;
        {
            response = new BankStatementListResponse();

            List<BankStatementResponse> content = mapper.bankStatementListToBankStatementListResponse(list.getContent());
            response.setContent(content);

            final int nextPage = list.getPageable().next().getPageNumber();
            response.setNextUri(ApiConstants.uriByPageAsString.apply(nextPage));

            final int previousPage = list.getPageable().previousOrFirst().getPageNumber();
            response.setPreviousUri(uriByPageAsString.apply(previousPage));

            response.setTotalPages(list.getTotalPages());
            response.setTotalElements(list.getTotalElements());
        }
        return ResponseEntity.ok().body(response);
    }

    @Override
    @GetMapping("/movement-type/{movementTypeId}")
    public ResponseEntity<BankStatementListResponse> getBankStatementsByMovementType(@PathVariable Long movementTypeId,
                                                                                     Optional<Integer> page,
                                                                                     Optional<Integer> size) {
        final int pageNumber = page.filter(p -> p > 0).orElse(DEFAULT_PAGE);
        final int pageSize = size.filter(s -> s > 0).orElse(DEFAULT_PAGE_SIZE);

        BankStatementList list = service.getBankStatementByMovementType(movementTypeId, PageRequest.of(pageNumber, pageSize));

        BankStatementListResponse response;
        {
            response = new BankStatementListResponse();

            List<BankStatementResponse> content = mapper.bankStatementListToBankStatementListResponse(list.getContent());
            response.setContent(content);

            final int nextPage = list.getPageable().next().getPageNumber();
            response.setNextUri(ApiConstants.uriByPageAsString.apply(nextPage));

            final int previousPage = list.getPageable().previousOrFirst().getPageNumber();
            response.setPreviousUri(uriByPageAsString.apply(previousPage));

            response.setTotalPages(list.getTotalPages());
            response.setTotalElements(list.getTotalElements());
        }
        return ResponseEntity.ok().body(response);
    }

}
