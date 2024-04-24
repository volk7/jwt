package com.polarphoenix.jwtservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="token")
    private String token;

    @Column(name="is_logged_out")
    private boolean loggedOut;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
