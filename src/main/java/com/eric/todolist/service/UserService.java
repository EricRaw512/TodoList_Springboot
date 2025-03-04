package com.eric.todolist.service;

import com.eric.todolist.exception.GlobalException;
import com.eric.todolist.exception.InvalidCredentialsException;
import com.eric.todolist.model.dto.request.UserRequest;
import com.eric.todolist.model.entity.Users;
import com.eric.todolist.model.enums.Role;
import com.eric.todolist.repository.UserRepository;
import com.eric.todolist.util.constant.GlobalMessage;
import com.eric.todolist.util.enums.StatusCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<Users> loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Users authenticateUser(String username, String password) {
        Optional<Users> user = loadUserByUsername(username);
        if (user.isEmpty() || !passwordEncoder.matches(password, user.get().getPassword())) {
            throw new InvalidCredentialsException(GlobalMessage.ExceptionMessage.USERNAME_OR_PASSWORD_WRONG);
        }

        return user.get();
    }

    public void registerUser(String username, String password) {
        Users newUser = new Users();
        newUser.setUsername(username);
        String encodedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encodedPassword);
        newUser.setRole(Role.ROLE_USER);
        userRepository.save(newUser);
    }

    public Page<Users> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Users updateUserUsername(int userId, @Valid UserRequest request) {
        Optional<Users> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new GlobalException(StatusCode.BAD_REQUEST, String.format(GlobalMessage.ExceptionMessage.USER_NOT_EXIST, userId));
        }

        user.get().setUsername(request.getUsername());
        return userRepository.save(user.get());
    }
}
