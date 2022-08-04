package com.nicouema.bank.ports.input.rs.mapper;

import com.nicouema.bank.domain.model.Branch;
import com.nicouema.bank.domain.model.BranchList;
import com.nicouema.bank.ports.input.rs.request.CreateBranchRequest;
import com.nicouema.bank.ports.input.rs.request.UpdateBranchRequest;
import com.nicouema.bank.ports.input.rs.response.BranchListResponse;
import com.nicouema.bank.ports.input.rs.response.BranchResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface BranchControllerMapper {

    @Mapping(target = "city.id", source = "city")
    Branch createBranchRequestToBranch(CreateBranchRequest createBranchRequest);

    BranchResponse branchToBranchResponse(Branch branch);

    @Mapping(target = "city.id", source = "city")
    Branch updateBranchRequestToBranch(UpdateBranchRequest updateBranchRequest);

    List<BranchResponse> branchListToBranchListResponse(List<Branch> branchList);
}
