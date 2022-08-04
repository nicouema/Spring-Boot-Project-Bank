package com.nicouema.bank.domain.usecase;

import com.nicouema.bank.domain.model.Branch;
import com.nicouema.bank.domain.model.BranchList;
import com.nicouema.bank.domain.model.ClientList;
import org.springframework.data.domain.PageRequest;

public interface BranchService {

    Long createBranch(Branch branch);

    Branch getByIdIfExists(Long id);

    void updateBranch(Long id, Branch branch);

    void deleteBranch(Long id);

    ClientList getClientsFromBranch(Long branchId, PageRequest pageRequest);

    BranchList getAllBranches(PageRequest pageRequest);
}
