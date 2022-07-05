package com.nicouema.bank.ports.input.rs.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentTypeRequest {

    @NotBlank
    private String description;
}
