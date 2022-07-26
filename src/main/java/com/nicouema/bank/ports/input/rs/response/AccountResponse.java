package com.nicouema.bank.ports.input.rs.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {


    @JsonProperty("minimum_balance_allowed")
    private Double minimumBalanceAllowed;

    @JsonProperty("initial_balance")
    private Double initialBalance;

    private Set<BankStatementResponse> statements;

    private Double debt;

    @JsonProperty("current_balance")
    private Double currentBalance;
}
