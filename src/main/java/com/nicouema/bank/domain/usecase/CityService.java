package com.nicouema.bank.domain.usecase;

import com.nicouema.bank.domain.model.City;
import com.nicouema.bank.domain.model.CityList;
import org.springframework.data.domain.PageRequest;

public interface CityService {

    Long createCity(City city);

    City getCityById(Long id);
    void updateCity(Long id, City city);
    void deleteCity(Long city);

    CityList getAllCities(PageRequest pageRequest);
}
