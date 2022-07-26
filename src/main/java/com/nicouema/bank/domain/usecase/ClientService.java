package com.nicouema.bank.domain.usecase;

import com.nicouema.bank.domain.model.Client;
import com.nicouema.bank.domain.model.ClientList;
import com.nicouema.bank.domain.model.User;
import org.springframework.data.domain.PageRequest;

public interface ClientService {

    Client createClient(Client client, User user, Long documentTypeId);

    Client updateClient(Long id, Client client);

    Client getClientByIdIfExist(Long id);

    ClientList getAllClients(PageRequest pageRequest);

    void deleteClientById(Long id);
}
