package com.nicouema.bank.domain.usecase.impl;

import com.nicouema.bank.common.exception.ConflictException;
import com.nicouema.bank.common.exception.NotFoundException;
import com.nicouema.bank.domain.model.Role;
import com.nicouema.bank.domain.model.User;
import com.nicouema.bank.domain.repository.RoleRepository;
import com.nicouema.bank.domain.repository.UserRepository;
import com.nicouema.bank.domain.usecase.BranchService;
import com.nicouema.bank.domain.usecase.UserService;
import com.nicouema.bank.ports.output.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.executable.ValidateOnExecution;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final EmailService emailService;

    private final BranchService branchService;

    @Value("${default.role.id}")
    private Long defaultRoleUser;

    @Value("${default.branch.id}")
    private Long defaultBranchId;

    @Override
    public User registerUser(User user) {
        if (emailExists(user.getEmail())){
            throw new ConflictException("There's already a user registered with the email: " + user.getEmail());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(getRoleIfExists(user.getRole().getId()));

        emailService.sendWelcome(user.getEmail(), branchService.getByIdIfExists(defaultBranchId));

        return userRepository.save(user);
    }

    private Role getRoleIfExists(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException(roleId));
    }

    public boolean emailExists(String email){
        return userRepository.findByEmail(email).isPresent();
    }

}
