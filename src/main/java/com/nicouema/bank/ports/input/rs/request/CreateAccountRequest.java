package com.nicouema.bank.ports.input.rs.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequest {

    @JsonProperty("minimum_balance_allowed")
    private Double minimumBalanceAllowed;

    @JsonProperty("initial_balance")
    private Double initialBalance;

    @JsonProperty("branch_id")
    private Long branchId;

}
