package com.nicouema.bank.ports.input.rs.api;

import com.nicouema.bank.ports.input.rs.request.AuthenticationRequest;
import com.nicouema.bank.ports.input.rs.request.CreateClientRequest;
import com.nicouema.bank.ports.input.rs.request.CreateUserRequest;
import com.nicouema.bank.ports.input.rs.response.AuthenticationResponse;
import com.nicouema.bank.ports.input.rs.response.UserAndAuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface AuthenticationApi {

    ResponseEntity<AuthenticationResponse> login(@Valid AuthenticationRequest authenticationRequest);
    ResponseEntity<UserAndAuthenticationResponse> registerNewUser(@Valid CreateUserRequest userRequest);
}
