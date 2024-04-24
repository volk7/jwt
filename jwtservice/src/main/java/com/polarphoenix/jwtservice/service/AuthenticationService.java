package com.polarphoenix.jwtservice.service;

import com.polarphoenix.jwtservice.dto.AuthenticationRequest;
import com.polarphoenix.jwtservice.dto.AuthenticationResponse;
import com.polarphoenix.jwtservice.dto.RefreshTokenRequest;
import com.polarphoenix.jwtservice.dto.SignupRequest;
import com.polarphoenix.jwtservice.entities.User;

public interface AuthenticationService {

    public AuthenticationResponse signup(SignupRequest signupRequest);
    public AuthenticationResponse login(AuthenticationRequest loginRequest);
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
