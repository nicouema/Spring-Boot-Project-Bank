package com.nicouema.bank.domain.usecase.impl;

import com.nicouema.bank.common.exception.ConflictException;
import com.nicouema.bank.common.exception.NotFoundException;
import com.nicouema.bank.domain.model.*;
import com.nicouema.bank.domain.repository.ClientRepository;
import com.nicouema.bank.domain.repository.DocumentTypeRepository;
import com.nicouema.bank.domain.usecase.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final DocumentTypeRepository documentTypeRepository;

    @Override
    @Transactional
    public Client createClient(Client client, User user, Long documentTypeId) throws ConflictException {

        if (user.getClient() == null) {
            DocumentType documentType = getDocumentTypeIfExist(documentTypeId);

            client.setDocType(documentType);
            clientRepository.save(client);
            return client;
        }
        throw new ConflictException("There is already a client registered with the current user!");
    }

    @Override
    @Transactional
    public Client updateClient(Long id, Client client) {
        Client clientToUpdate = getClientByIdIfExist(id);

        if (client.getDocType().getId() != null) {
            DocumentType documentType = getDocumentTypeIfExist(client.getDocType().getId());
            clientToUpdate.setDocType(documentType);
        }
        if (client.getIdNumber() != null) {
            clientToUpdate.setIdNumber(client.getIdNumber());
        }
        if (client.getName() != null) {
            clientToUpdate.setName(client.getName());
        }
        if (client.getLastname() != null) {
            clientToUpdate.setLastname(client.getLastname());
        }
        if (client.getPhoneNumber() != null) {
            clientToUpdate.setPhoneNumber(client.getPhoneNumber());
        }
        if (client.getStreetName() != null) {
            clientToUpdate.setStreetName(client.getStreetName());
        }
        if (client.getStreetNumber() != null) {
            clientToUpdate.setStreetNumber(client.getStreetNumber());
        }

        clientRepository.save(clientToUpdate);

        return clientToUpdate;
    }

    @Override
    @Transactional
    public Client getClientByIdIfExist(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    @Transactional
    public ClientList getAllClients(PageRequest pageRequest) {
        Page<Client> page = clientRepository.findAll(pageRequest);
        return new ClientList(page.getContent(), pageRequest, page.getTotalElements());
    }

    @Override
    @Transactional
    public void deleteClientById(Long id) {
        clientRepository.deleteById(id);
    }

    private DocumentType getDocumentTypeIfExist(Long documentTypeId) {

        return documentTypeRepository.findById(documentTypeId).orElseThrow(() -> new NotFoundException(documentTypeId));

    }
}
