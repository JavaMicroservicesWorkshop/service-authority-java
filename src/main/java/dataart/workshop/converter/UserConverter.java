package dataart.workshop.converter;

import dataart.workshop.domain.User;
import dataart.workshop.dto.v1.UserDto;
import dataart.workshop.dto.v1.UserRegistrationRequest;
import dataart.workshop.dto.v1.PaginatedUserDto;
import dataart.workshop.dto.v1.UpdateUserRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserConverter {

    public UserDto toUserDto(User user) {
        UserDto dto = new UserDto();

        dto.setId(user.getUserId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());

        return dto;
    }

    public PaginatedUserDto toPaginatedDto(Page<User> usersPage) {
        PaginatedUserDto dto = new PaginatedUserDto();

        List<UserDto> userDtoList = usersPage.get()
                .map(this::toUserDto)
                .toList();

        dto.setData(userDtoList);
        dto.setPage(usersPage.getNumber());
        dto.setTotalPages(usersPage.getTotalPages());
        dto.setSize(usersPage.getNumberOfElements());

        return dto;
    }

    public User toUser(UpdateUserRequest request) {
        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());

        return user;
    }

    public User toUser(UserRegistrationRequest userRegistrationRequest, String userId) {
        User user = new User();

        user.setUserId(userId);

        user.setEmail(userRegistrationRequest.getEmail());
        user.setPassword(userRegistrationRequest.getPassword());

        user.setFirstName(userRegistrationRequest.getFirstName());
        user.setLastName(userRegistrationRequest.getLastName());

        user.setPhoneNumber(userRegistrationRequest.getPhoneNumber());

        return user;
    }
}
