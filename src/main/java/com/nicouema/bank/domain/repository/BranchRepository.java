package com.nicouema.bank.domain.repository;

import com.nicouema.bank.domain.model.Branch;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BranchRepository extends PagingAndSortingRepository<Branch, Long> {
}
