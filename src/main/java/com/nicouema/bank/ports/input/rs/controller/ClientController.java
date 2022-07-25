package com.nicouema.bank.ports.input.rs.controller;

import com.nicouema.bank.domain.model.Client;
import com.nicouema.bank.domain.model.User;
import com.nicouema.bank.domain.usecase.ClientService;
import com.nicouema.bank.domain.usecase.impl.ClientServiceImpl;
import com.nicouema.bank.ports.input.rs.api.ClientApi;
import com.nicouema.bank.ports.input.rs.mapper.ClientControllerMapper;
import com.nicouema.bank.ports.input.rs.request.CreateClientRequest;
import com.nicouema.bank.ports.input.rs.response.ClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.nicouema.bank.ports.input.rs.api.ApiConstants.CLIENT_URI;

@RestController
@RequestMapping(CLIENT_URI)
@RequiredArgsConstructor
public class ClientController implements ClientApi {

    private final ClientService clientService;

    private final ClientControllerMapper mapper;


    @Override
    @PostMapping
    public ResponseEntity<ClientResponse> createClient(@RequestBody CreateClientRequest clientRequest,
                                                       @AuthenticationPrincipal User user) {

        Client client = mapper.clientCreateRequestToClient(clientRequest);
        client.setUser(user);

        client = clientService.createClient(client, user, clientRequest.getDocumentTypeId());

        ClientResponse response = mapper.clientToClientResponse(client);

        return ResponseEntity.ok(response);
    }
}
