package com.nicouema.bank.ports.input.rs.controller;

import com.nicouema.bank.domain.model.DocumentType;
import com.nicouema.bank.domain.model.DocumentTypeList;
import com.nicouema.bank.domain.usecase.DocumentTypeService;
import com.nicouema.bank.ports.input.rs.api.ApiConstants;
import com.nicouema.bank.ports.input.rs.api.DocumentTypeApi;
import com.nicouema.bank.ports.input.rs.mapper.DocumentTypeControllerMapper;
import com.nicouema.bank.ports.input.rs.response.DocumentTypeListResponse;
import com.nicouema.bank.ports.input.rs.request.DocumentTypeRequest;
import com.nicouema.bank.ports.input.rs.response.DocumentTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.nicouema.bank.ports.input.rs.api.ApiConstants.DOCUMENT_TYPE_URI;
import static com.nicouema.bank.ports.input.rs.api.ApiConstants.uriByPageAsString;

@RestController
@RequestMapping(DOCUMENT_TYPE_URI)
@RequiredArgsConstructor
public class DocumentTypeController implements DocumentTypeApi {

    private final DocumentTypeService service;
    private final DocumentTypeControllerMapper mapper;

    @Override
    @PostMapping
    public ResponseEntity<Void> createDocumentType(@RequestBody DocumentTypeRequest createDocumentTypeRequest) {
        DocumentType documentType = mapper.documentTypeRequestToDocumentType(createDocumentTypeRequest);

        final Long id = service.createDocumentType(documentType);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}").buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<DocumentTypeResponse> getDocumentType(@PathVariable Long id) {
        DocumentType documentType = service.getByIdIfExists(id);
        DocumentTypeResponse documentTypeResponse = mapper.documentTypeToDocumentTypeResponse(documentType);
        return ResponseEntity.ok(documentTypeResponse);
    }

    @Override
    @GetMapping
    public ResponseEntity<DocumentTypeListResponse> getAllDocumentTypes(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size) {

        final int pageNumber = page.filter(p -> p > 0).orElse(ApiConstants.DEFAULT_PAGE);
        final int pageSize = page.filter(s -> s > 0).orElse(ApiConstants.DEFAULT_PAGE_SIZE);

        DocumentTypeList list = service.getDocumentList(PageRequest.of(pageNumber, pageSize));

        DocumentTypeListResponse response;
        {
            response = new DocumentTypeListResponse();

            List<DocumentTypeResponse> content = mapper.documentTypeListToDocumentTypeResponseList(list.getContent());
            response.setContent(content);

            final int nextPage = list.getPageable().next().getPageNumber();
            response.setNextUri(ApiConstants.uriByPageAsString.apply(nextPage));

            final int previousPage = list.getPageable().previousOrFirst().getPageNumber();
            response.setPreviousUri(uriByPageAsString.apply(previousPage));

            response.setTotalPages(list.getTotalPages());
            response.setTotalElements(list.getTotalElements());
        }
        return ResponseEntity.ok().body(response);
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDocumentTypeIfExist(@PathVariable Long id, @RequestBody DocumentTypeRequest updateDocumentTypeRequest) {
        DocumentType documentType = mapper.documentTypeRequestToDocumentType(updateDocumentTypeRequest);

        service.updateDocumentTypeIFExists(id, documentType);

    }
}
