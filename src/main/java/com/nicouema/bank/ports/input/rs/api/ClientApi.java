package com.nicouema.bank.ports.input.rs.api;

import com.nicouema.bank.domain.model.User;
import com.nicouema.bank.ports.input.rs.request.CreateClientRequest;
import com.nicouema.bank.ports.input.rs.request.UpdateClientRequest;
import com.nicouema.bank.ports.input.rs.response.AccountListResponse;
import com.nicouema.bank.ports.input.rs.response.ClientListResponse;
import com.nicouema.bank.ports.input.rs.response.ClientResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Validated
public interface ClientApi {

    ResponseEntity<ClientResponse> createClient(@Valid CreateClientRequest clientRequest, User user);

    ResponseEntity<ClientResponse> updateClient(@NotNull Long id, @Valid UpdateClientRequest updateClientRequest);

    ResponseEntity<ClientResponse> getClientById(@NotNull Long id);

    ResponseEntity<ClientListResponse> getAllClients(Optional<Integer> page, Optional<Integer> size);

    ResponseEntity<Void> deleteClientById(@NotNull Long id);

    ResponseEntity<ClientResponse> getMe(User user);

    ResponseEntity<AccountListResponse> getMyAccounts(User user, Optional<Integer> page, Optional<Integer> size);
}
