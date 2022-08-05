package com.nicouema.bank.ports.input.rs.api;

import com.nicouema.bank.ports.input.rs.request.CityRequest;
import com.nicouema.bank.ports.input.rs.response.CityListResponse;
import com.nicouema.bank.ports.input.rs.response.CityResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Validated
public interface CityApi {

    ResponseEntity<Void> createCity(@Valid CityRequest cityRequest);

    ResponseEntity<CityResponse> getCityById(@NotNull Long id);

    ResponseEntity<Void> updateCity(@NotNull Long id, @Valid CityRequest cityRequest);

    ResponseEntity<Void> deleteCityById(@NotNull Long id);

    ResponseEntity<CityListResponse> getAllCities(Optional<Integer> page,
                                                  Optional<Integer> size);
}
