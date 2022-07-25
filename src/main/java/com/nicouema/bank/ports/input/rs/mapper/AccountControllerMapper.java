package com.nicouema.bank.ports.input.rs.mapper;

import com.nicouema.bank.domain.model.Account;
import com.nicouema.bank.ports.input.rs.request.CreateAccountRequest;
import com.nicouema.bank.ports.input.rs.response.AccountResponse;
import org.mapstruct.Mapper;

@Mapper
public interface AccountControllerMapper {

    Account createAccountRequestToAccount(CreateAccountRequest createAccountRequest);

    AccountResponse accountToAccountResponse(Account account);
}
