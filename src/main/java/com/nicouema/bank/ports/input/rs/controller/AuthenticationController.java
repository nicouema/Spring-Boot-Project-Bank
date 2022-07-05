package com.nicouema.bank.ports.input.rs.controller;

import com.nicouema.bank.domain.model.User;
import com.nicouema.bank.domain.usecase.UserService;
import com.nicouema.bank.ports.input.rs.api.AuthenticationApi;
import com.nicouema.bank.ports.input.rs.mapper.UserControllerMapper;
import com.nicouema.bank.ports.input.rs.request.CreateUserRequest;
import com.nicouema.bank.ports.input.rs.response.AuthenticationResponse;
import com.nicouema.bank.ports.input.rs.response.UserAndAuthenticationResponse;
import com.nicouema.bank.ports.input.rs.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.nio.file.AccessDeniedException;

import static com.nicouema.bank.ports.input.rs.api.ApiConstants.AUTHENTICATION_URI;

@RestController
@RequestMapping(AUTHENTICATION_URI)
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationApi {

    private final AuthenticationManager authenticationManager;

    private final UserControllerMapper mapper;

    private final UserService userService;

    @Override
    @PostMapping("/register")
    public ResponseEntity<UserAndAuthenticationResponse> registerNewUser(@RequestBody CreateUserRequest userRequest) {

        User user = mapper.createUserRequestToUser(userRequest);
        user = userService.registerUser(user);

        UserResponse userResponse = mapper.userToUserResponse(user);
        AuthenticationResponse authenticationResponse = prepareAuthenticationResponse(user.getEmail(),
                user.getPassword());

        var response = new UserAndAuthenticationResponse(userResponse, authenticationResponse);

        final Long id = user.getId();

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}").buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @SneakyThrows
    private AuthenticationResponse prepareAuthenticationResponse(String username, String password) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails users) {


            return AuthenticationResponse.builder()
                    .build();
        }

        throw new AccessDeniedException("error in the authentication process");
    }
}
