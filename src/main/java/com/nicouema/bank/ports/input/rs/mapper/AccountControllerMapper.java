package com.nicouema.bank.ports.input.rs.mapper;

import com.nicouema.bank.domain.model.Account;
import com.nicouema.bank.ports.input.rs.request.CreateAccountRequest;
import com.nicouema.bank.ports.input.rs.response.AccountDownloadResponse;
import com.nicouema.bank.ports.input.rs.response.AccountResponse;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface AccountControllerMapper {

    Account createAccountRequestToAccount(CreateAccountRequest createAccountRequest);

    AccountResponse accountToAccountResponse(Account account);

    List<AccountResponse> accountListToAccountListResponse(List<Account> list);

    @Mapping(target = "clientName", source = "client_.name")
    @Mapping(target = "clientLastname", source = "client_.lastname")
    AccountDownloadResponse accountToAccountDownloadResponse(Account account);

    List<AccountDownloadResponse> accountListToAccountDownloadResponse(List<Account> accounts);
}
