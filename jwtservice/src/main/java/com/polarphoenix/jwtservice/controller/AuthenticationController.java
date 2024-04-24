package com.polarphoenix.jwtservice.controller;

import com.polarphoenix.jwtservice.dto.AuthenticationRequest;
import com.polarphoenix.jwtservice.dto.AuthenticationResponse;
import com.polarphoenix.jwtservice.dto.RefreshTokenRequest;
import com.polarphoenix.jwtservice.dto.SignupRequest;
import com.polarphoenix.jwtservice.entities.User;
import com.polarphoenix.jwtservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signup(@RequestBody SignupRequest signupRequest){
        return ResponseEntity.ok(authenticationService.signup(signupRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest loginRequest){
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshRequest));
    }
}
