package com.nicouema.bank.ports.input.rs.api;

import com.nicouema.bank.ports.input.rs.request.CreateBranchRequest;
import com.nicouema.bank.ports.input.rs.request.UpdateBranchRequest;
import com.nicouema.bank.ports.input.rs.response.BranchListResponse;
import com.nicouema.bank.ports.input.rs.response.BranchResponse;
import com.nicouema.bank.ports.input.rs.response.ClientListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Validated
public interface BranchApi {

    ResponseEntity<Void> createBranch(CreateBranchRequest createBranchRequest);

    ResponseEntity<BranchResponse> getBranchById(@NotNull Long id);

    ResponseEntity<Void> updateBranch(@NotNull Long id, @Valid UpdateBranchRequest updateBranchRequest);

    ResponseEntity<Void> deleteBranch(@NotNull Long id);

    ResponseEntity<ClientListResponse> getClientsFromBranch(@NotNull Long id,
                                                            Optional<Integer> page,
                                                            Optional<Integer> size);

    ResponseEntity<BranchListResponse> getAllBranches(Optional<Integer> page,
                                                      Optional<Integer> size);
}
