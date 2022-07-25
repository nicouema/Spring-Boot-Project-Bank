package com.nicouema.bank.domain.usecase;

import com.nicouema.bank.domain.model.MovementType;
import com.nicouema.bank.domain.model.MovementTypeList;
import org.springframework.data.domain.PageRequest;

public interface MovementTypeService {

    Long createMovementType(MovementType movementType);

    void updateMovementType(Long id, MovementType movementType);

    void deleteMovementType(Long id);

    MovementType getMovementTypeById(Long id);

    MovementTypeList getMovementTypeList(PageRequest pageRequest);
}
