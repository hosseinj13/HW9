package model;

import base.model.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString


public class Admin extends BaseEntity<Integer> {
    String name;
    String username;
    String email;
    String password;

    public Admin(Integer adminId, String name, String username, String email, String password) {
        super(adminId);
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
