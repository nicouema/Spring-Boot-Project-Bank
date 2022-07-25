package com.nicouema.bank.domain.usecase.impl;

import com.nicouema.bank.common.exception.NotFoundException;
import com.nicouema.bank.domain.model.MovementType;
import com.nicouema.bank.domain.model.MovementTypeList;
import com.nicouema.bank.domain.repository.MovementTypeRepository;
import com.nicouema.bank.domain.usecase.MovementTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MovementTypeServiceImpl implements MovementTypeService {

    private final MovementTypeRepository movementTypeRepository;


    @Override
    @Transactional
    public Long createMovementType(MovementType movementType) {
        return movementTypeRepository.save(movementType).getId();
    }

    @Override
    @Transactional
    public void updateMovementType(Long id, MovementType movementType) {
        movementTypeRepository.findById(id).map(
                movementTypeUpdated -> {
                    movementTypeUpdated.setName(movementType.getName());
                    movementTypeUpdated.setStatements(movementType.getStatements());
                return movementTypeRepository.save(movementTypeUpdated);
                }
        ).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    @Transactional
    public void deleteMovementType(Long id) {
        movementTypeRepository.findById(id).ifPresent(movementTypeRepository::delete);
    }

    @Override
    @Transactional
    public MovementType getMovementTypeById(Long id) {
        return movementTypeRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public MovementTypeList getMovementTypeList(PageRequest pageRequest) {

        Page<MovementType> page = movementTypeRepository.findAll(pageRequest);

        return new MovementTypeList(page.getContent(), pageRequest, page.getTotalElements());
    }
}
