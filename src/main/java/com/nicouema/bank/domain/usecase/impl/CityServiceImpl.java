package com.nicouema.bank.domain.usecase.impl;

import com.nicouema.bank.common.exception.NotFoundException;
import com.nicouema.bank.domain.model.City;
import com.nicouema.bank.domain.model.CityList;
import com.nicouema.bank.domain.repository.CityRepository;
import com.nicouema.bank.domain.usecase.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    @Transactional
    public Long createCity(City city) {
        return cityRepository.save(city).getId();
    }

    @Override
    @Transactional
    public City getCityById(Long id) {
        return cityRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    @Transactional
    public void updateCity(Long id, City city) {
        cityRepository.findById(id).map(
                cityToUpdate -> {
                    cityToUpdate.setName(city.getName());
                    return cityRepository.save(cityToUpdate);
                }
        ).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    @Transactional
    public void deleteCity(Long id) {
        cityRepository.findById(id).ifPresent(cityRepository::delete);
    }

    @Override
    @Transactional
    public CityList getAllCities(PageRequest pageRequest) {
        Page<City> page = cityRepository.findAll(pageRequest);

        return new CityList(page.getContent(), pageRequest, page.getTotalElements());
    }
}
