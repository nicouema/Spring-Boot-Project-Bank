package com.nicouema.bank.ports.input.rs.controller;

import com.nicouema.bank.domain.model.Client;
import com.nicouema.bank.domain.model.ClientList;
import com.nicouema.bank.domain.model.User;
import com.nicouema.bank.domain.usecase.ClientService;
import com.nicouema.bank.ports.input.rs.api.ClientApi;
import com.nicouema.bank.ports.input.rs.mapper.ClientControllerMapper;
import com.nicouema.bank.ports.input.rs.request.CreateClientRequest;
import com.nicouema.bank.ports.input.rs.request.UpdateClientRequest;
import com.nicouema.bank.ports.input.rs.response.ClientListResponse;
import com.nicouema.bank.ports.input.rs.response.ClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.nicouema.bank.ports.input.rs.api.ApiConstants.*;

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

        Client client = mapper.createClientRequestToClient(clientRequest);
        client.setUser(user);

        client = clientService.createClient(client, user, clientRequest.getDocumentTypeId());

        ClientResponse response = mapper.clientToClientResponse(client);

        return ResponseEntity.ok(response);
    }

    @Override
    @PatchMapping("{id}")
    public ResponseEntity<ClientResponse> updateClient(@PathVariable Long id,
                                                       @RequestBody UpdateClientRequest updateClientRequest) {
        Client client = mapper.updateClientRequestToClient(updateClientRequest);

        client = clientService.updateClient(id, client);

        ClientResponse response = mapper.clientToClientResponse(client);

        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("{id}")
    public ResponseEntity<ClientResponse> getClientById(@PathVariable Long id) {
        Client client = clientService.getClientByIdIfExist(id);

        ClientResponse response = mapper.clientToClientResponse(client);

        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping
    public ResponseEntity<ClientListResponse> getAllClients(@PathVariable Optional<Integer> page,
                                                            @PathVariable Optional<Integer> size) {
        final int pageNumber = page.filter( p -> p > 0 ).orElse(DEFAULT_PAGE);
        final int pageSize = page.filter( s -> s > 0 ).orElse(DEFAULT_PAGE_SIZE);

        ClientList list = clientService.getAllClients(PageRequest.of(pageNumber, pageSize));

        ClientListResponse response;
        {
            response = new ClientListResponse();

            List<ClientResponse> content = mapper.clientListToClientResponseList(list.getContent());
            response.setContent(content);

            final int nextPage = list.getPageable().next().getPageNumber();
            response.setNextUri(uriByPageAsString.apply(nextPage));

            final int previousPage = list.getPageable().previousOrFirst().getPageNumber();
            response.setPreviousUri(uriByPageAsString.apply(previousPage));

            response.setTotalPages(list.getTotalPages());
            response.setTotalElements(list.getTotalElements());
        }
        return ResponseEntity.ok().body(response);
    }

    @Override
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteClientById(@PathVariable Long id) {
        clientService.deleteClientById(id);
            
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
