package com.nicouema.bank.domain.repository;

import com.nicouema.bank.domain.model.DocumentType;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DocumentTypeRepository extends PagingAndSortingRepository<DocumentType, Long> {
}
