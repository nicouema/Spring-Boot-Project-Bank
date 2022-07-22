package com.nicouema.bank.ports.input.rs.request;

import javax.validation.constraints.NotBlank;

public record AuthenticationRequest(

    @NotBlank(message = "The username must not be blank")
    String username,
    @NotBlank(message = "The password must not be blank")
    String password)
{
}
