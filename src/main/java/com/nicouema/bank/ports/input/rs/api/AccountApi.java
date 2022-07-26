package com.nicouema.bank.ports.input.rs.api;

import com.nicouema.bank.domain.model.User;
import com.nicouema.bank.ports.input.rs.request.CreateAccountRequest;
import com.nicouema.bank.ports.input.rs.response.AccountListResponse;
import com.nicouema.bank.ports.input.rs.response.AccountResponse;
import com.nicouema.bank.ports.input.rs.response.BankStatementListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

@Validated
public interface AccountApi {

    ResponseEntity<Void> createAccount(@Valid CreateAccountRequest createAccountRequest,
                                       User user);

    ResponseEntity<AccountResponse> getAccountById(@Valid Long account_id, @Valid Long branch_id);

    ResponseEntity<BankStatementListResponse> getBankStatementDesc(@Valid Long account_id, @Valid Long branch_id,
                                                                   Optional<Integer> page, Optional<Integer> size);

    ResponseEntity<AccountListResponse> getAllAccounts(Optional<Integer> page, Optional<Integer> size);

}
