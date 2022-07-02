package com.nicouema.bank.domain.repository;

import com.nicouema.bank.domain.model.MovementType;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MovementTypeRepository extends PagingAndSortingRepository<MovementType, Long> {
}
