package com.nicouema.bank.domain.usecase.impl;

import com.nicouema.bank.common.exception.NotFoundException;
import com.nicouema.bank.domain.model.DocumentType;
import com.nicouema.bank.domain.model.DocumentTypeList;
import com.nicouema.bank.domain.repository.DocumentTypeRepository;
import com.nicouema.bank.domain.usecase.DocumentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private final DocumentTypeRepository documentTypeRepository;

    @Override
    @Transactional
    public Long createDocumentType(DocumentType documentType) {
        return documentTypeRepository.save(documentType).getId();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        documentTypeRepository.findById(id).ifPresent(documentTypeRepository::delete);
    }

    @Override
    @Transactional
    public DocumentType getByIdIfExists(Long id) {
        return documentTypeRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    @Transactional
    public void updateDocumentTypeIFExists(Long id, DocumentType documentType) {
        documentTypeRepository.findById(id)
                .map(
                        documentTypeUpdated -> {
                            documentTypeUpdated.setDescription(documentType.getDescription());
                            return documentTypeRepository.save(documentTypeUpdated);
            }).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    @Transactional
    public DocumentTypeList getDocumentList(PageRequest pageRequest) {
        Page<DocumentType> page = documentTypeRepository.findAll(pageRequest);

        return new DocumentTypeList(page.getContent(), pageRequest, page.getTotalElements());
    }
}
