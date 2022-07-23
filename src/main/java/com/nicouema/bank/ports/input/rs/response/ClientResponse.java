package com.nicouema.bank.ports.input.rs.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponse {

    @JsonProperty("doc_type_id")
    private DocumentTypeResponse docType;

    @JsonProperty("id_number")
    private String idNumber;

    private String name;

    @JsonProperty("last_name")
    private String lastname;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("street_name")
    private String streetName;

    @JsonProperty("street_number")
    private Integer streetNumber;
}
