package com.nicouema.bank.domain.usecase;

import com.nicouema.bank.domain.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {

    User registerUser(User user);

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
