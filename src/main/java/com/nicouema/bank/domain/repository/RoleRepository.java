package com.nicouema.bank.domain.repository;

import com.nicouema.bank.domain.model.Role;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
}
