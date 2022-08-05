package com.nicouema.bank.ports.input.rs.mapper;

import com.nicouema.bank.domain.model.City;
import com.nicouema.bank.ports.input.rs.request.CityRequest;
import com.nicouema.bank.ports.input.rs.response.CityResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CityControllerMapper {

    CityResponse cityToCityResponse(City city);

    City cityRequestToCity(CityRequest cityRequest);

    List<CityResponse> cityListToCityResponseList(List<City> cities);

}
