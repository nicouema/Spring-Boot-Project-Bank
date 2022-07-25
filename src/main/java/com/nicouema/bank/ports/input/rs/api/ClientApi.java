package com.nicouema.bank.ports.input.rs.api;

import com.nicouema.bank.domain.model.User;
import com.nicouema.bank.ports.input.rs.request.CreateClientRequest;
import com.nicouema.bank.ports.input.rs.response.ClientResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface ClientApi {

    ResponseEntity<ClientResponse> createClient(@Valid CreateClientRequest clientRequest, User user);
}
