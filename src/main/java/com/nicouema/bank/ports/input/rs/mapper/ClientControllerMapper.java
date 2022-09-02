package com.nicouema.bank.ports.input.rs.mapper;

import com.nicouema.bank.domain.model.Client;
import com.nicouema.bank.ports.input.rs.request.CreateClientRequest;
import com.nicouema.bank.ports.input.rs.request.UpdateClientRequest;
import com.nicouema.bank.ports.input.rs.response.ClientDownloadResponse;
import com.nicouema.bank.ports.input.rs.response.ClientListResponse;
import com.nicouema.bank.ports.input.rs.response.ClientResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface ClientControllerMapper {

    Client createClientRequestToClient(CreateClientRequest clientRequest);

    ClientResponse clientToClientResponse(Client client);

    @Mapping(target = "docType.id", source = "documentTypeId")
    Client updateClientRequestToClient(UpdateClientRequest updateClientRequest);

    List<ClientResponse> clientListToClientResponseList(List<Client> clientList);

    @Mapping(target = "documentDescription", source = "docType.description")
    ClientDownloadResponse clientDownloadResponse(Client client);

    List<ClientDownloadResponse> clientListToClientDownloadResponseList(List<Client> list);
}
