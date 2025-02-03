package org.yawdenisk.woodlit.Entites;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
