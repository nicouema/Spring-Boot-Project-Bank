package com.nicouema.bank.ports.input.rs.controller;

import com.nicouema.bank.domain.model.Branch;
import com.nicouema.bank.domain.model.BranchList;
import com.nicouema.bank.domain.model.ClientList;
import com.nicouema.bank.domain.usecase.BranchService;
import com.nicouema.bank.ports.input.rs.api.ApiConstants;
import com.nicouema.bank.ports.input.rs.api.BranchApi;
import com.nicouema.bank.ports.input.rs.mapper.BranchControllerMapper;
import com.nicouema.bank.ports.input.rs.mapper.ClientControllerMapper;
import com.nicouema.bank.ports.input.rs.request.CreateBranchRequest;
import com.nicouema.bank.ports.input.rs.request.UpdateBranchRequest;
import com.nicouema.bank.ports.input.rs.response.BranchListResponse;
import com.nicouema.bank.ports.input.rs.response.BranchResponse;
import com.nicouema.bank.ports.input.rs.response.ClientListResponse;
import com.nicouema.bank.ports.input.rs.response.ClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.nicouema.bank.ports.input.rs.api.ApiConstants.*;
import static com.nicouema.bank.ports.input.rs.api.ApiConstants.DEFAULT_PAGE_SIZE;

@RestController
@RequestMapping(BRANCH_URI)
@RequiredArgsConstructor
public class BranchController implements BranchApi {

    private final BranchService branchService;

    private final BranchControllerMapper mapper;

    private final ClientControllerMapper clientMapper;

    @Override
    @PostMapping
    public ResponseEntity<Void> createBranch(@RequestBody CreateBranchRequest createBranchRequest) {
        Branch branch = mapper.createBranchRequestToBranch(createBranchRequest);
        Long id = branchService.createBranch(branch);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}").buildAndExpand(id).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    @GetMapping("{id}")
    public ResponseEntity<BranchResponse> getBranchById(@PathVariable Long id) {
        Branch branch = branchService.getByIdIfExists(id);

        BranchResponse response = mapper.branchToBranchResponse(branch);

        return ResponseEntity.ok().body(response);
    }

    @Override
    @PutMapping("{id}")
    public ResponseEntity<Void> updateBranch(@PathVariable Long id,
                                             @RequestBody UpdateBranchRequest updateBranchRequest) {

        Branch branch = mapper.updateBranchRequestToBranch(updateBranchRequest);

        branchService.updateBranch(id, branch);
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        branchService.deleteBranch(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/{id}/clients")
    public ResponseEntity<ClientListResponse> getClientsFromBranch(@PathVariable Long id,
                                                                   @RequestParam Optional<Integer> page,
                                                                   @RequestParam Optional<Integer> size) {

        final int pageNumber = page.filter(p -> p > 0).orElse(ApiConstants.DEFAULT_PAGE);
        final int pageSize = page.filter(s -> s > 0).orElse(ApiConstants.DEFAULT_PAGE_SIZE);

        ClientList list = branchService.getClientsFromBranch(id, PageRequest.of(pageNumber, pageSize));

        ClientListResponse response;
        {
            response = new ClientListResponse();

            List<ClientResponse> content = clientMapper.clientListToClientResponseList(list.getContent());
            response.setContent(content);

            final int nextPage = list.getPageable().next().getPageNumber();
            response.setNextUri(uriByPageAsString.apply(nextPage));

            final int previousPage = list.getPageable().previousOrFirst().getPageNumber();
            response.setPreviousUri(uriByPageAsString.apply(previousPage));

            response.setTotalPages(list.getTotalPages());
            response.setTotalElements(list.getTotalElements());
        }
        return ResponseEntity.ok().body(response);
    }

    @Override
    @GetMapping
    public ResponseEntity<BranchListResponse> getAllBranches(Optional<Integer> page, Optional<Integer> size) {

        final int pageNumber = page.filter( p -> p > 0 ).orElse(DEFAULT_PAGE);
        final int pageSize = size.filter( s -> s > 0 ).orElse(DEFAULT_PAGE_SIZE);

        BranchList list = branchService.getAllBranches(PageRequest.of(pageNumber, pageSize));

        BranchListResponse response;
        {
            response = new BranchListResponse();

            List<BranchResponse> content = mapper.branchListToBranchListResponse(list.getContent());
            response.setContent(content);

            final int nextPage = list.getPageable().next().getPageNumber();
            response.setNextUri(uriByPageAsString.apply(nextPage));

            final int previousPage = list.getPageable().previousOrFirst().getPageNumber();
            response.setPreviousUri(uriByPageAsString.apply(previousPage));

            response.setTotalPages(list.getTotalPages());
            response.setTotalElements(list.getTotalElements());
        }
        return ResponseEntity.ok().body(response);

    }
}
