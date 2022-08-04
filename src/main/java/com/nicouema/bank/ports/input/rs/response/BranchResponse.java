package com.nicouema.bank.ports.input.rs.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nicouema.bank.domain.model.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchResponse {

    private String name;

    @JsonProperty("street_name")
    private String streetName;

    @JsonProperty("street_number")
    private Integer streetNumber;

    private String email;

    private CityResponse city;


}
