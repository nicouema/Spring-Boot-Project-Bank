package com.nicouema.bank.domain.usecase.impl;

import com.nicouema.bank.common.exception.NotFoundException;
import com.nicouema.bank.domain.model.Branch;
import com.nicouema.bank.domain.model.BranchList;
import com.nicouema.bank.domain.model.Client;
import com.nicouema.bank.domain.model.ClientList;
import com.nicouema.bank.domain.repository.BranchRepository;
import com.nicouema.bank.domain.usecase.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;

    @Override
    @Transactional
    public Long createBranch(Branch branch) {
        return branchRepository.save(branch).getId();
    }

    @Override
    @Transactional
    public Branch getByIdIfExists(Long id) {
        return branchRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    @Transactional
    public void updateBranch(Long id, Branch branch) {
        branchRepository.findById(id).map(
                branchToUpdate -> {
                    branchToUpdate.setName(branch.getName());
                    branchToUpdate.setCity(branch.getCity());
                    branchToUpdate.setEmail(branch.getEmail());
                    branchToUpdate.setStreetNumber(branch.getStreetNumber());
                    branchToUpdate.setStreetName(branch.getStreetName());
                    return branchRepository.save(branchToUpdate);
                }
        ).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    @Transactional
    public void deleteBranch(Long id) {
        branchRepository.findById(id).ifPresent(branchRepository::delete);
    }

    @Override
    @Transactional
    public ClientList getClientsFromBranch(Long branchId, PageRequest pageRequest) {
        Branch branch = branchRepository.findById(branchId).orElseThrow(() -> new NotFoundException(branchId));
        List<Client> clientList = branch.getClients();
        return new ClientList(clientList, pageRequest, clientList.size());
    }

    @Override
    @Transactional
    public BranchList getAllBranches(PageRequest pageRequest) {
        Page<Branch> page = branchRepository.findAll(pageRequest);
        return new BranchList(page.getContent(), pageRequest, page.getTotalElements());
    }
}
