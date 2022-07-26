package com.nicouema.bank.domain.usecase.impl;

import com.nicouema.bank.common.exception.ConflictException;
import com.nicouema.bank.common.exception.NotFoundException;
import com.nicouema.bank.domain.model.Client;
import com.nicouema.bank.domain.model.DocumentType;
import com.nicouema.bank.domain.model.User;
import com.nicouema.bank.domain.repository.ClientRepository;
import com.nicouema.bank.domain.repository.DocumentTypeRepository;
import com.nicouema.bank.domain.usecase.ClientService;
import lombok.RequiredArgsConstructor;
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

    private DocumentType getDocumentTypeIfExist(Long documentTypeId) {

        return documentTypeRepository.findById(documentTypeId).orElseThrow(() -> new NotFoundException(documentTypeId));

    }
}
