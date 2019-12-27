package com.oauth.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * A User.
 */
@Entity
@Table(name = "oauth_user")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;


}
