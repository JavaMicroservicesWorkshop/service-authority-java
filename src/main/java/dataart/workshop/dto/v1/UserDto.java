package dataart.workshop.dto.v1;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Role role;

}
