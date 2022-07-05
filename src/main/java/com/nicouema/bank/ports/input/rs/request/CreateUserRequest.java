package com.nicouema.bank.ports.input.rs.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 16)
    private String password;
}
