package com.eric.todolist.controller;

import com.eric.todolist.exception.UserAlreadyExistException;
import com.eric.todolist.mapper.GlobalResponseTransform;
import com.eric.todolist.mapper.JwtTransform;
import com.eric.todolist.model.dto.request.LoginRequest;
import com.eric.todolist.model.dto.request.RegisterRequest;
import com.eric.todolist.model.dto.response.GlobalResponse;
import com.eric.todolist.model.entity.Users;
import com.eric.todolist.service.JwtService;
import com.eric.todolist.service.UserService;
import com.eric.todolist.util.constant.GlobalMessage;
import com.eric.todolist.util.enums.StatusCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final UserService userService;
    private final JwtService jwtService;
    private final GlobalResponseTransform globalResponseTransform;
    private final JwtTransform jwtTransform;

    @Operation(summary = "User login", description = "Authenticate user and generate JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful, JWT token generated",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GlobalResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - invalid login data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized - invalid credentials", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping(
            value = "/login",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> login(
            @Valid @RequestBody @Parameter(description = "Login request object", required = true, schema = @Schema(implementation = LoginRequest.class)) LoginRequest loginRequest
    ) {
        Users user = userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
        String jwtToken = jwtService.generateToken(user);
        return ResponseEntity.ok(globalResponseTransform.generateResponse(
                LocalDateTime.now(),
                StatusCode.OK,
                jwtTransform.toJwtResponse(jwtToken)
        ));
    }

    @Operation(summary = "User registration", description = "Register a new user account.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = GlobalMessage.Response.Authentication.CREATE_USER,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(example = "{\"timestamp\": \"2024-07-24T12:00:00\", \"statusCode\": \"OK\", \"message\": \"User registered successfully\", \"data\": null}"))), // Example response
            @ApiResponse(responseCode = "400", description = "Bad request - invalid registration data", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict - username already exists", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping(
            value = "register",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> register(
            @Valid @RequestBody @Parameter(description = "Registration request object", required = true, schema = @Schema(implementation = GlobalResponse.class)) RegisterRequest registerRequest
    ) {
        if (userService.loadUserByUsername(registerRequest.getUsername()).isPresent()) {
            throw new UserAlreadyExistException(GlobalMessage.ExceptionMessage.USER_ALREADY_EXIST);
        }

        userService.registerUser(registerRequest.getUsername(), registerRequest.getPassword());
        return ResponseEntity.ok(globalResponseTransform.generateResponse(
                LocalDateTime.now(),
                StatusCode.OK,
                GlobalMessage.Response.Authentication.CREATE_USER,
                null
        ));
    }
}