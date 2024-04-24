package com.polarphoenix.jwtservice.repo;

import com.polarphoenix.jwtservice.entities.Role;
import com.polarphoenix.jwtservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    User findByRole(Role role);
}
