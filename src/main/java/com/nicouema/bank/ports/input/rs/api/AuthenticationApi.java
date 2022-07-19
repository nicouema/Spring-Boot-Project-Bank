package com.nicouema.bank.ports.input.rs.api;

import com.nicouema.bank.ports.input.rs.request.CreateUserRequest;
import com.nicouema.bank.ports.input.rs.response.UserAndAuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Validated
public interface AuthenticationApi {


    ResponseEntity<UserAndAuthenticationResponse> registerNewUser(@Valid @RequestBody CreateUserRequest userRequest);
}
