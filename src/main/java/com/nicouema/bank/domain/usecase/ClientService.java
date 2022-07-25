package com.nicouema.bank.domain.usecase;

import com.nicouema.bank.domain.model.Client;
import com.nicouema.bank.domain.model.User;

public interface ClientService {

    Client createClient(Client client, User user, Long documentTypeId);
}
