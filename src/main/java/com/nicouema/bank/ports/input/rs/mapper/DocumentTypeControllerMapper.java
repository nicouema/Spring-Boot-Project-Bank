package com.nicouema.bank.ports.input.rs.mapper;

import com.nicouema.bank.domain.model.DocumentType;
import com.nicouema.bank.ports.input.rs.request.DocumentTypeRequest;
import com.nicouema.bank.ports.input.rs.response.DocumentTypeResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface DocumentTypeControllerMapper{

    List<DocumentTypeResponse> documentTypeListToDocumentTypeResponseList(List<DocumentType> list);

    DocumentTypeResponse documentTypeToDocumentTypeResponse(DocumentType documentType);

    DocumentType documentTypeRequestToDocumentType(DocumentTypeRequest documentTypeRequest);
}
