package com.nicouema.bank.ports.input.rs.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankStatementDownloadResponse {

    private String movementType;
    private Double amount;
    private String clientName;
    private String clientLastname;
}
