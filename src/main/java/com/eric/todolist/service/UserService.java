package com.eric.todolist.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eric.todolist.dao.UserRepository;
import com.eric.todolist.dto.UserDto;
import com.eric.todolist.entity.Role;
import com.eric.todolist.entity.User;
import com.eric.todolist.exception.UserException;
import com.eric.todolist.exception.UsernameOrPasswordException;
import com.eric.todolist.security.UserDetail;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User authenticateUser(String username, String password) {
        Optional<User> user = loadUserByUsername(username);
        if (!user.isPresent() || !passwordEncoder.matches(password, user.get().getPassword())) {
            throw new UsernameOrPasswordException("Username or password is wrong");
        }
        
        if (!user.get().isEnabled()) {
        	throw new DisabledException("Your account is disabled");
        }
        
        return user.get();
    }

    public void registerUser(String username, String password) {
        User newUser = new User();
        newUser.setUsername(username);
        String encodedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encodedPassword);
        newUser.setRole(Role.ROLE_USER);
        newUser.setAccountNonExpired(true);
        newUser.setAccountNonLocked(true);
        newUser.setEnabled(true);
        userRepository.save(newUser);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
            .map(this::converToDto)
            .toList();
    }

    public UserDto updateUserPassword(int userId, UserDto userDto, UserDetail currentUser) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new UserException("Cannot Find User with id" + userId);
        }
        
        if (!passwordEncoder.matches(userDto.getOldPassword(), user.get().getPassword()) && !currentUser.hasRole("ROLE_ADMIN")) {
        	throw new UsernameOrPasswordException("Old password is wrong");
        }
        
        user.get().setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user.get());
        return userDto;
    }
    
	public void updateUserStatus(int userId) {
		Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new UserException("Cannot Find User with id" + userId);
        }
        
        user.get().setEnabled(!user.get().isEnabled());
        userRepository.save(user.get());
	}

    private UserDto converToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setRole(user.getRole());
        userDto.setUsername(user.getUsername());
        userDto.setAccountNonExpired(user.isAccountNonExpired());
        userDto.setAccountNonLocked(user.isAccountNonLocked());
        userDto.setEnabled(user.isEnabled());
        return userDto;
    }
}
