package com.polarphoenix.jwtservice.service.impl;

import com.polarphoenix.jwtservice.dto.AuthenticationRequest;
import com.polarphoenix.jwtservice.dto.AuthenticationResponse;
import com.polarphoenix.jwtservice.dto.RefreshTokenRequest;
import com.polarphoenix.jwtservice.dto.SignupRequest;
import com.polarphoenix.jwtservice.entities.Role;
import com.polarphoenix.jwtservice.entities.Token;
import com.polarphoenix.jwtservice.entities.User;
import com.polarphoenix.jwtservice.repo.TokenRepo;
import com.polarphoenix.jwtservice.repo.UserRepo;
import com.polarphoenix.jwtservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final TokenRepo tokenRepo;

    public AuthenticationResponse signup(SignupRequest signupRequest){
        User user = new User();

        user.setEmail(signupRequest.getEmail());
        user.setFirstname(signupRequest.getFirstName());
        user.setLastname(signupRequest.getLastName());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        userRepo.save(user);

        String jwtToken = jwtService.generateToken(user);
        revokeAllTokensByUser(user);
        SaveToken(user, jwtToken);

        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        return AuthenticationResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build();

    }

    public AuthenticationResponse login(AuthenticationRequest loginRequest){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        var user = userRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        String jwtToken = jwtService.generateToken(user);
        revokeAllTokensByUser(user);
        SaveToken(user, jwtToken);
        Role role = user.getRole();
        boolean isAdmin = role.equals(Role.ADMIN); // Assuming Role.ADMIN is the role for administrators
        boolean isUser = role.equals(Role.USER);

        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
        return AuthenticationResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).isAdmin(isAdmin).build();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String userEmail = jwtService.extractUsername(refreshTokenRequest.getToken());
        User user = userRepo.findByEmail(userEmail).orElseThrow();
        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)){
            String jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshTokenRequest.getToken())
                    .build();
        }
        return null;
    }

    private void SaveToken(User user, String jwtToken) {
        Token token = new Token();
        token.setToken(jwtToken);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepo.save(token);
    }
    private void revokeAllTokensByUser(User user) {
        List<Token> validTokenListByUser = tokenRepo.findAllTokensByUser(user.getId());
        if (!validTokenListByUser.isEmpty()) {
            validTokenListByUser.forEach(t -> {
                t.setLoggedOut(true);
            });
        }
        tokenRepo.saveAll(validTokenListByUser);
    }
}
