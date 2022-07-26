package com.nicouema.bank.domain.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class MovementTypeList extends PageImpl<MovementType> {

    public MovementTypeList(List<MovementType> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
