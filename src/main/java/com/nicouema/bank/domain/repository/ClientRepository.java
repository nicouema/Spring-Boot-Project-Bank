package com.nicouema.bank.domain.repository;

import com.nicouema.bank.domain.model.Client;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {
}
