package com.nicouema.bank.ports.input.rs.api;

import com.nicouema.bank.common.exception.error.ErrorDetails;
import com.nicouema.bank.ports.input.rs.request.CreateBranchRequest;
import com.nicouema.bank.ports.input.rs.request.UpdateBranchRequest;
import com.nicouema.bank.ports.input.rs.response.BranchListResponse;
import com.nicouema.bank.ports.input.rs.response.BranchResponse;
import com.nicouema.bank.ports.input.rs.response.ClientListResponse;
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
public interface BranchApi {

    @Operation(summary = "Create new branch", description = "Create a new branch", responses = {
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
    })
    ResponseEntity<Void> createBranch(@Valid CreateBranchRequest createBranchRequest);

    ResponseEntity<BranchResponse> getBranchById(@NotNull Long id);

    ResponseEntity<Void> updateBranch(@NotNull Long id, @Valid UpdateBranchRequest updateBranchRequest);

    ResponseEntity<Void> deleteBranch(@NotNull Long id);

    ResponseEntity<ClientListResponse> getClientsFromBranch(@NotNull Long id,
                                                            Optional<Integer> page,
                                                            Optional<Integer> size);

    ResponseEntity<BranchListResponse> getAllBranches(Optional<Integer> page,
                                                      Optional<Integer> size);
}
