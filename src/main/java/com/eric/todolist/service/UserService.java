package com.eric.todolist.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eric.todolist.dao.UserRepository;
import com.eric.todolist.dto.UserDto;
import com.eric.todolist.entity.Role;
import com.eric.todolist.entity.User;
import com.eric.todolist.exception.UserException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User authenticateUser(String username, String password) {
        User user = loadUserByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        
        return null;
    }

    public void registerUser(String username, String password) {
        User newUser = new User();
        newUser.setUsername(username);
        String encodedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encodedPassword);
        newUser.setRole(Role.ROLE_USER);
        userRepository.save(newUser);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
            .map(user -> ConverToDto(user))
            .collect(Collectors.toList());
    }

    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserDto updateUserUsername(int userId, UserDto userDto) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new UserException("Cannot Find User with id" + userId);
        }

        user.get().setUsername(userDto.getUsername());
        userRepository.save(user.get());
        return userDto;
    }

    private UserDto ConverToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setRole(user.getRole());
        userDto.setUsername(user.getUsername());
        return userDto;
    }
}
