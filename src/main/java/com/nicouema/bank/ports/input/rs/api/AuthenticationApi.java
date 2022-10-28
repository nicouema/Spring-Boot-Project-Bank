package com.nicouema.bank.ports.input.rs.api;

import com.nicouema.bank.common.exception.error.ErrorDetails;
import com.nicouema.bank.ports.input.rs.request.AuthenticationRequest;
import com.nicouema.bank.ports.input.rs.request.CreateUserRequest;
import com.nicouema.bank.ports.input.rs.response.AuthenticationResponse;
import com.nicouema.bank.ports.input.rs.response.UserAndAuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface AuthenticationApi {

    @Operation(summary = "User Login", description ="User Login", responses = {
            @ApiResponse(responseCode = "200" , description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AuthenticationResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class)),
                            examples = @ExampleObject(value = "[{\"code\":\"INVALID_FIELD_VALUE\",\"detail\":\"must not be blank\",\"field\":\"username\",\"location\":\"BODY\"}]"))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDetails.class),
                            examples = @ExampleObject(value = "{\"code\":\"BAD_CREDENTIALS\",\"detail\":\"The server cannot return a response due to invalid credentials.\"}"))})
    })
    ResponseEntity<AuthenticationResponse> login(@Valid AuthenticationRequest authenticationRequest);

    @Operation(summary = "Create User", description = "Create User", responses = {
            @ApiResponse(responseCode = "201" , description = "Created",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserAndAuthenticationResponse.class))}),
            @ApiResponse(responseCode = "409", description = "Conflict",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class)),
                            examples = @ExampleObject(value ="{\"code\":\"RESOURCE_ALREADY_EXISTS\",\"detail\":\"There is already an account with that email address: admin1@somosmas.org.\"}" ))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class)),
                            examples = @ExampleObject(value = "[{\"code\":\"INVALID_FIELD_VALUE\",\"detail\":\"el tamaño debe estar entre 8 y 16\",\"field\":\"password\",\"location\":\"BODY\"}]"))}),

    })
    ResponseEntity<UserAndAuthenticationResponse> registerNewUser(@Valid CreateUserRequest userRequest);
}
