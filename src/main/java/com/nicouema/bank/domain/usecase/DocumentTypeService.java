package com.nicouema.bank.domain.usecase;

import com.nicouema.bank.domain.model.DocumentType;
import com.nicouema.bank.domain.model.DocumentTypeList;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface DocumentTypeService {

    Long createDocumentType(DocumentType documentType);

    void deleteById(Long id);

    DocumentType getByIdIfExists(Long id);

    void updateDocumentTypeIFExists(Long id, DocumentType documentType);

    DocumentTypeList getDocumentList(PageRequest pageRequest);
}
