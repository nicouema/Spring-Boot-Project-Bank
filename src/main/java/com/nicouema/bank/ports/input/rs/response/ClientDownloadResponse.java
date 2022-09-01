package com.nicouema.bank.ports.input.rs.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDownloadResponse {

    private String documentDescription;
    private String idNumber;
    private String name;
    private String lastname;
    private String phoneNumber;
    private String streetName;
    private Integer streetNumber;

}
