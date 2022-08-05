package com.nicouema.bank.domain.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class CityList extends PageImpl<City> {
    public CityList(List<City> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
