package com.nicouema.bank.ports.input.rs.request;

import com.nicouema.bank.domain.model.MovementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankStatementRequest {

    private MovementType movementType;

    private Double amount;
}
