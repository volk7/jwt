package com.polarphoenix.jwtservice.repo;

import com.polarphoenix.jwtservice.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepo extends JpaRepository<Token, Long> {

    @Query("""
        SELECT t FROM Token t INNER JOIN t.user u
        WHERE u.id = :userId AND t.loggedOut = false
            """)
    List<Token> findAllTokensByUser(Long userId);

    Optional<Token> findByToken(String token);
}
