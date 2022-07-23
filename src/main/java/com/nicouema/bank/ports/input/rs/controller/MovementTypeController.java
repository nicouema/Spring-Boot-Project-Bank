package com.nicouema.bank.ports.input.rs.controller;

import com.nicouema.bank.domain.model.MovementType;
import com.nicouema.bank.domain.model.MovementTypeList;
import com.nicouema.bank.domain.usecase.MovementTypeService;
import com.nicouema.bank.ports.input.rs.api.MovementTypeApi;
import com.nicouema.bank.ports.input.rs.mapper.MovementTypeControllerMapper;
import com.nicouema.bank.ports.input.rs.request.MovementTypeRequest;
import com.nicouema.bank.ports.input.rs.response.MovementTypeListResponse;
import com.nicouema.bank.ports.input.rs.response.MovementTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.nicouema.bank.ports.input.rs.api.ApiConstants.*;

@RestController
@RequestMapping(MOVEMENT_TYPE_URI)
@RequiredArgsConstructor
public class MovementTypeController implements MovementTypeApi {

    private final MovementTypeService service;

    private final MovementTypeControllerMapper mapper;

    @Override
    @PostMapping
    public ResponseEntity<Void> createMovementType(@RequestBody MovementTypeRequest movementTypeRequest) {
        MovementType movementType = mapper.movementTypeRequestToMovementType(movementTypeRequest);

        final Long id = service.createMovementType(movementType);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}").buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMovementType(@PathVariable Long id, @RequestBody MovementTypeRequest updateMovementTypeRequest) {

        MovementType movementType = mapper.movementTypeRequestToMovementType(updateMovementTypeRequest);

        service.updateMovementType(id, movementType);

    }

    @Override
    @DeleteMapping("{id}")
    public void deleteMovementType(Long id) {
        service.deleteMovementType(id);
    }

    @Override
    @GetMapping("{id}")
    public ResponseEntity<MovementTypeResponse> getMovementTypeById(@PathVariable Long id) {
        MovementType movementType = service.getMovementTypeById(id);

        MovementTypeResponse movementTypeResponse = mapper.movementTypeToMovementTypeResponse(movementType);

        return ResponseEntity.ok(movementTypeResponse);
    }

    @Override
    @GetMapping
    public ResponseEntity<MovementTypeListResponse> getAllMovementType(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size) {

        final int pageNumber = page.filter(p -> p > 0).orElse(DEFAULT_PAGE);
        final int pageSize = size.filter(s -> s > 0).orElse(DEFAULT_PAGE_SIZE);

        MovementTypeList list = service.getMovementTypeList(PageRequest.of(pageNumber, pageSize));

        MovementTypeListResponse response;
        {
            response = new MovementTypeListResponse();

            List<MovementTypeResponse> content = mapper.movementTypeListToMovementTypeListResponse(list.getContent());
            response.setContent(content);

            final int previousPage = list.getPageable().previousOrFirst().getPageNumber();
            response.setPreviousUri(uriByPageAsString.apply(previousPage));

            final int nextPage = list.getPageable().next().getPageNumber();
            response.setNextUri(uriByPageAsString.apply(nextPage));

            response.setTotalElements(list.getTotalElements());
            response.setTotalPages(list.getTotalPages());
        }
        return ResponseEntity.ok().body(response);
    }
}
