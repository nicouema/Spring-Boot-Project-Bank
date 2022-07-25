package com.nicouema.bank.ports.input.rs.api;

import com.nicouema.bank.domain.model.MovementType;
import com.nicouema.bank.domain.model.MovementTypeList;
import com.nicouema.bank.ports.input.rs.request.MovementTypeRequest;
import com.nicouema.bank.ports.input.rs.response.MovementTypeListResponse;
import com.nicouema.bank.ports.input.rs.response.MovementTypeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Validated
public interface MovementTypeApi {

    ResponseEntity<Void> createMovementType(@Valid MovementTypeRequest movementTypeRequest);

    void updateMovementType(@NotNull Long id, @Valid MovementTypeRequest updateMovementTypeRequest);

    void deleteMovementType(@NotNull Long id);

    ResponseEntity<MovementTypeResponse> getMovementTypeById(@NotNull Long id);

    ResponseEntity<MovementTypeListResponse> getAllMovementType(Optional<Integer> page, Optional<Integer> size);
}
