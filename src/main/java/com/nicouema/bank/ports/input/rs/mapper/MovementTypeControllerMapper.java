package com.nicouema.bank.ports.input.rs.mapper;

import com.nicouema.bank.domain.model.DocumentType;
import com.nicouema.bank.domain.model.MovementType;
import com.nicouema.bank.domain.model.MovementTypeList;
import com.nicouema.bank.ports.input.rs.request.MovementTypeRequest;
import com.nicouema.bank.ports.input.rs.response.MovementTypeListResponse;
import com.nicouema.bank.ports.input.rs.response.MovementTypeResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MovementTypeControllerMapper {

    List<MovementTypeResponse> movementTypeListToMovementTypeListResponse(List<MovementType> list);

    MovementType movementTypeRequestToMovementType(MovementTypeRequest movementTypeRequest);

    MovementTypeResponse movementTypeToMovementTypeResponse(MovementType movementType);
}
