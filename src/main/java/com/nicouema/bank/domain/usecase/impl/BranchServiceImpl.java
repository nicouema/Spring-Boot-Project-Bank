package com.nicouema.bank.domain.usecase.impl;

import com.nicouema.bank.common.exception.NotFoundException;
import com.nicouema.bank.domain.model.Branch;
import com.nicouema.bank.domain.repository.BranchRepository;
import com.nicouema.bank.domain.usecase.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;

    @Override
    public Branch getByIdIfExists(Long id) {
        return branchRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }
}
