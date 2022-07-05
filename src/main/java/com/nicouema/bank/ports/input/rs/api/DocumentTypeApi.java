package com.nicouema.bank.ports.input.rs.api;

import com.nicouema.bank.ports.input.rs.request.DocumentTypeListResponse;
import com.nicouema.bank.ports.input.rs.request.DocumentTypeRequest;
import com.nicouema.bank.ports.input.rs.response.DocumentTypeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Validated
public interface DocumentTypeApi {

    ResponseEntity<Void> createDocumentType(@Valid DocumentTypeRequest createDocumentTypeRequest);

    void deleteById(@NotNull Long id);

    ResponseEntity<DocumentTypeResponse> getDocumentType(@NotNull Long id);

    ResponseEntity<DocumentTypeListResponse> getAllDocumentTypes(Optional<Integer> page, Optional<Integer> size);

    void updateDocumentTypeIfExist( @NotNull Long id, DocumentTypeRequest updateDocumentTypeRequest);
}
