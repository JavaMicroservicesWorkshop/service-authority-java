package dataart.workshop.dto.v1;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerDto {

    String id;
    String firstName;
    String lastName;
    String phoneNumber;
    String email;

}
