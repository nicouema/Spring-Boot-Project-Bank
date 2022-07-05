package com.nicouema.bank.ports.input.rs.mapper;

import com.nicouema.bank.domain.model.User;
import com.nicouema.bank.ports.input.rs.request.CreateUserRequest;
import com.nicouema.bank.ports.input.rs.response.AuthenticationResponse;
import com.nicouema.bank.ports.input.rs.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper
public interface UserControllerMapper {

    UserResponse userToUserResponse(User user);

    User userResponseToUser(UserResponse userResponse);

    User createUserRequestToUser(CreateUserRequest createUserRequest);
}
