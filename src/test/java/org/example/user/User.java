package org.example.user;

import lombok.*;

@Data
@Builder
public class User {
    private String email;
    private String password;
    private String name;

    public static User generic() {
        return new User("porovozik@toster.by", "p@ssword1", "TosterBY");
    }
}