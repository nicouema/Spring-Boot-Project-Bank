package com.nicouema.bank.ports.input.rs.api;

import com.nicouema.bank.domain.model.User;
import com.nicouema.bank.ports.input.rs.request.BankStatementRequest;
import com.nicouema.bank.ports.input.rs.response.BankStatementListResponse;
import com.nicouema.bank.ports.input.rs.response.BankStatementResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Validated
public interface BankStatementApi {

    // TODO
    ResponseEntity<Void> createBankStatement(@Valid BankStatementRequest createBankStatementRequest,
                                             @Valid Long branchId,
                                             @Valid Long accountId,
                                             User user);

    ResponseEntity<BankStatementResponse> updateBankStatement(@NotNull Long id ,
                                                              @Valid BankStatementRequest updateBankStatementRequest);

    ResponseEntity<BankStatementResponse> getBankStatementById(@NotNull Long id);

    ResponseEntity<BankStatementListResponse> getAllBankStatements(Optional<Integer> page, Optional<Integer> size);

    ResponseEntity<BankStatementListResponse> getBankStatementsByMovementType(Long movementTypeId, Optional<Integer> page, Optional<Integer> size);

}
