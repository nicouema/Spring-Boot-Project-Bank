package com.nicouema.bank.domain.usecase;

import com.nicouema.bank.domain.model.Branch;

public interface BranchService {

    Branch getByIdIfExists(Long id);
}
