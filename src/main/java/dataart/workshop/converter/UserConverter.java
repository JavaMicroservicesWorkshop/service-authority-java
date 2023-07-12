package dataart.workshop.converter;

import dataart.workshop.domain.User;
import dataart.workshop.dto.v1.PaginatedUserDto;
import dataart.workshop.dto.v1.Role;
import dataart.workshop.dto.v1.UpdateUserRequest;
import dataart.workshop.dto.v1.UserDto;
import dataart.workshop.dto.v1.UserRegistrationRequest;
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
        dto.setRole(user.getRole());

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
        user.setRole(Role.valueOf(request.getRole()));

        return user;
    }

    public User toUser(UserRegistrationRequest request, String userId) {
        User user = new User();

        user.setUserId(userId);

        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        user.setPhoneNumber(request.getPhoneNumber());

        user.setRole(Role.valueOf(request.getRole()));

        return user;
    }
}
