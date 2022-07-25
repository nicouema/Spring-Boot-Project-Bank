package com.nicouema.bank.ports.input.rs.mapper;

import com.nicouema.bank.domain.model.Client;
import com.nicouema.bank.ports.input.rs.request.CreateClientRequest;
import com.nicouema.bank.ports.input.rs.response.ClientResponse;
import org.mapstruct.Mapper;

@Mapper
public interface ClientControllerMapper {

    Client clientCreateRequestToClient(CreateClientRequest clientRequest);

    ClientResponse clientToClientResponse(Client client);
}
