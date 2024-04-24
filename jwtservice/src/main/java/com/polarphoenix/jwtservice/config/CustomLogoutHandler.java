package com.polarphoenix.jwtservice.config;

import com.polarphoenix.jwtservice.repo.TokenRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import com.polarphoenix.jwtservice.entities.Token;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomLogoutHandler implements LogoutHandler {

    @Autowired
    private TokenRepo tokenRepo;

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication){

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        String jwt = authHeader.substring(7);

        Optional<Token> optionalToken = tokenRepo.findByToken(jwt);
        if (optionalToken.isPresent()) {
            Token storedToken = optionalToken.get();
            storedToken.setLoggedOut(true);
            tokenRepo.save(storedToken);
        }
        System.out.println("Logout");
    }
}
