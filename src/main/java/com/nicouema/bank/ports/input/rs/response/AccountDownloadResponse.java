package com.nicouema.bank.ports.input.rs.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDownloadResponse {

    private String clientName;
    private String clientLastname;
    private Double minimumBalance;
    private Double currentBalance;
    private String initialBalance;
    private String debt;
}
