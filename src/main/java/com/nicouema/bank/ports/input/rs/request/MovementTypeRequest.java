package com.nicouema.bank.ports.input.rs.request;

import com.nicouema.bank.domain.model.BankStatement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovementTypeRequest {

    private String name;

}
