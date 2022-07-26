package com.nicouema.bank.ports.input.rs.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateClientRequest {

    @JsonProperty("doc_type_id")
    @NotNull
    @Positive
    private Long documentTypeId;

    @NotBlank(message = "Id Number must not be blank")
    @JsonProperty("id_number")
    private String idNumber;

    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotBlank(message = "Lastname must not be blank")
    private String lastname;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("street_name")
    private String streetName;

    @JsonProperty("street_number")
    private Integer streetNumber;

}
