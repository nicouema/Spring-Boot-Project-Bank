package com.nicouema.bank.ports.input.rs.api;

import com.nicouema.bank.common.exception.error.ErrorDetails;
import com.nicouema.bank.domain.model.User;
import com.nicouema.bank.ports.input.rs.request.BankStatementRequest;
import com.nicouema.bank.ports.input.rs.response.BankStatementListResponse;
import com.nicouema.bank.ports.input.rs.response.BankStatementResponse;
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
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Validated
public interface BankStatementApi {

    @Operation(summary = "Create new statement", description = "Create a new statement", responses = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class)),
                            examples = @ExampleObject(value = "[{\"code\":\"INVALID_FIELD_VALUE\"," +
                                    "\"detail\":\"must not be blank\",\"field\":\"name\",\"location\":\"BODY\"}]"))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDetails.class),
                            examples = @ExampleObject(value = "{\"code\":\"BAD_CREDENTIALS\"," +
                                    "\"detail\":\"The server cannot return a response due to invalid credentials.\"}"))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDetails.class),
                            examples = @ExampleObject(value = "{\"code\":\"ROLE_INVALID\"," +
                                    "\"detail\":\"The user does not have access to the current resource\"}"))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class)),
                            examples = @ExampleObject(value = "[{\"code\":\"RESOURCE_ALREADY_EXISTS\"," +
                                    "\"detail\":\"The resource with id is not found.\"'"))}),
            @ApiResponse(responseCode = "409", description = "insufficient balance",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class)),
                            examples = @ExampleObject(value = "[{\"code\":\"INSUFFICIENT_BALANCE\"," +
                                    "\"detail\":\"There is not enough balance in your account.\"'"))})

    })
    ResponseEntity<Void> createBankStatement(@Valid BankStatementRequest createBankStatementRequest,
                                             @NotNull Long branchId,
                                             @NotNull Long accountId,
                                             User user);

    @Operation(summary = "Update statement", description = "Update a statement by id", responses = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class)),
                            examples = @ExampleObject(value = "[{\"code\":\"INVALID_FIELD_VALUE\"," +
                                    "\"detail\":\"must not be blank\",\"field\":\"name\",\"location\":\"BODY\"}]"))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDetails.class),
                            examples = @ExampleObject(value = "{\"code\":\"BAD_CREDENTIALS\"," +
                                    "\"detail\":\"The server cannot return a response due to invalid credentials.\"}"))}),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDetails.class),
                            examples = @ExampleObject(value = "{\"code\":\"ROLE_INVALID\"," +
                                    "\"detail\":\"The user does not have access to the current resource\"}"))}),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class)),
                            examples = @ExampleObject(value = "[{\"code\":\"RESOURCE_ALREADY_EXISTS\"," +
                                    "\"detail\":\"The resource with id is not found.\"'"))}),
            @ApiResponse(responseCode = "409", description = "insufficient balance",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class)),
                            examples = @ExampleObject(value = "[{\"code\":\"INSUFFICIENT_BALANCE\"," +
                                    "\"detail\":\"There is not enough balance in your account.\"'"))})

    })
    ResponseEntity<BankStatementResponse> updateBankStatement(@NotNull Long id ,
                                                              @Valid BankStatementRequest updateBankStatementRequest,
                                                              User user);

    @Operation(summary = "Get Statement", description = "Get Bank Statement by id", responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ErrorDetails.class)),
                            examples = @ExampleObject(value = "[{\"code\":\"RESOURCE_ALREADY_EXISTS\"," +
                                    "\"detail\":\"The resource with id is not found.\"'"))}),

    })
    ResponseEntity<BankStatementResponse> getBankStatementById(@NotNull Long id, User user);

    @Operation(summary = "Get All Statement", description = "Get Bank Statements", responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDetails.class),
                            examples = @ExampleObject(value = "{\"code\":\"ROLE_INVALID\"," +
                                    "\"detail\":\"The user does not have access to the current resource\"}"))})

    })
    ResponseEntity<BankStatementListResponse> getAllBankStatements(Optional<Integer> page, Optional<Integer> size);

    @Operation(summary = "Get Statements by Movement Type", description = "Get Statements by Movement Type id", responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDetails.class),
                            examples = @ExampleObject(value = "{\"code\":\"ROLE_INVALID\"," +
                                    "\"detail\":\"The user does not have access to the current resource\"}"))})

    })
    ResponseEntity<BankStatementListResponse> getBankStatementsByMovementType(Long movementTypeId, Optional<Integer> page, Optional<Integer> size);

}
