package com.nicouema.bank.domain.repository;

import com.nicouema.bank.domain.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
