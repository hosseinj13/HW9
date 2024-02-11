package model;

import base.model.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)

public class User extends BaseEntity<Integer> {
    String name;
    String username;
    String email;
    String password;

    public User(Integer userId, String name, String username, String email, String password) {
        super(userId);
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
