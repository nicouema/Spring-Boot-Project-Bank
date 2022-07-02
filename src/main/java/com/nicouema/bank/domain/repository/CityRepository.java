package com.nicouema.bank.domain.repository;

import com.nicouema.bank.domain.model.City;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CityRepository extends PagingAndSortingRepository<City, Long> {
}
