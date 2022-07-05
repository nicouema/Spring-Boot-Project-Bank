package com.nicouema.bank.domain.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class DocumentTypeList extends PageImpl<DocumentType> {
    public DocumentTypeList(List<DocumentType> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
