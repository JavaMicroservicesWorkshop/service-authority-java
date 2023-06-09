package dataart.workshop.converter;

import dataart.workshop.domain.User;
import dataart.workshop.dto.v1.RegistrationRequest;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public User toUser(RegistrationRequest registrationRequest) {
        User user = new User();

        user.setUsername(registrationRequest.getUsername());
        user.setPassword(registrationRequest.getPassword());

        return user;
    }
}
