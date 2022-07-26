package com.nicouema.bank.ports.input.rs.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapping;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankStatementRequest {

    @JsonProperty("movement_type_id")
    private Long movementType;

    private Double amount;
}
