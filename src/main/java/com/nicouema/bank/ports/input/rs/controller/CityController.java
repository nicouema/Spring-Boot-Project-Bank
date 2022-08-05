package com.nicouema.bank.ports.input.rs.controller;

import com.nicouema.bank.domain.model.BranchList;
import com.nicouema.bank.domain.model.City;
import com.nicouema.bank.domain.model.CityList;
import com.nicouema.bank.domain.usecase.CityService;
import com.nicouema.bank.ports.input.rs.api.CityApi;
import com.nicouema.bank.ports.input.rs.mapper.CityControllerMapper;
import com.nicouema.bank.ports.input.rs.request.CityRequest;
import com.nicouema.bank.ports.input.rs.response.BranchListResponse;
import com.nicouema.bank.ports.input.rs.response.BranchResponse;
import com.nicouema.bank.ports.input.rs.response.CityListResponse;
import com.nicouema.bank.ports.input.rs.response.CityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.nicouema.bank.ports.input.rs.api.ApiConstants.*;
import static com.nicouema.bank.ports.input.rs.api.ApiConstants.uriByPageAsString;

@RestController
@RequestMapping(CITY_URI)
@RequiredArgsConstructor
public class CityController implements CityApi {

    private final CityService cityService;

    private final CityControllerMapper mapper;

    @Override
    @PostMapping
    public ResponseEntity<Void> createCity(@RequestBody CityRequest cityRequest) {

        City city = mapper.cityRequestToCity(cityRequest);

        Long id = cityService.createCity(city);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}").buildAndExpand(id).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    @GetMapping("{id}")
    public ResponseEntity<CityResponse> getCityById(@PathVariable Long id) {
        City city = cityService.getCityById(id);

        CityResponse response = mapper.cityToCityResponse(city);

        return ResponseEntity.ok().body(response);
    }

    @Override
    @PutMapping("{id}")
    public ResponseEntity<Void> updateCity(@PathVariable Long id, @RequestBody CityRequest cityRequest) {
        City city = mapper.cityRequestToCity(cityRequest);
        cityService.updateCity(id, city);
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCityById(@PathVariable Long id) {
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping
    public ResponseEntity<CityListResponse> getAllCities(@RequestParam Optional<Integer> page,
                                                         @RequestParam Optional<Integer> size) {
        final int pageNumber = page.filter( p -> p > 0 ).orElse(DEFAULT_PAGE);
        final int pageSize = size.filter( s -> s > 0 ).orElse(DEFAULT_PAGE_SIZE);

        CityList list = cityService.getAllCities(PageRequest.of(pageNumber, pageSize));

        CityListResponse response;
        {
            response = new CityListResponse();

            List<CityResponse> content = mapper.cityListToCityResponseList(list.getContent());
            response.setContent(content);

            final int nextPage = list.getPageable().next().getPageNumber();
            response.setNextUri(uriByPageAsString.apply(nextPage));

            final int previousPage = list.getPageable().previousOrFirst().getPageNumber();
            response.setPreviousUri(uriByPageAsString.apply(previousPage));

            response.setTotalPages(list.getTotalPages());
            response.setTotalElements(list.getTotalElements());
        }
        return ResponseEntity.ok().body(response);
    }
}
