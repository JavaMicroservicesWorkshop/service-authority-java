package dataart.workshop.service;

import dataart.workshop.converter.UserConverter;
import dataart.workshop.domain.User;
import dataart.workshop.dto.v1.ChangePasswordRequest;
import dataart.workshop.dto.v1.PaginatedUserDto;
import dataart.workshop.dto.v1.UpdateUserRequest;
import dataart.workshop.dto.v1.UserDto;
import dataart.workshop.dto.v1.UserRegistrationRequest;
import dataart.workshop.dto.v1.UserRegistrationResponse;
import dataart.workshop.exception.UserNotFoundException;
import dataart.workshop.repository.UserRepository;
import dataart.workshop.utils.PageUtils;
import dataart.workshop.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String CANT_FIND_USER_ERROR = "Can't find user by id: %s";

    private final PageUtils pageUtils;
    private final UserValidator validator;
    private final UserConverter userConverter;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public PaginatedUserDto findAll(Integer page, Integer size) {
        Pageable pageable = pageUtils.adjustPageable(page, size);
        Page<User> usersPage = userRepository.findAll(pageable);

        return userConverter.toPaginatedDto(usersPage);
    }

    public UserDto findByUserId(String userId) {
        User user = findOrElseThrow(userId);

        return userConverter.toUserDto(user);
    }

    public UserRegistrationResponse register(UserRegistrationRequest userRegistrationRequest) {
        validator.validateUserDuplication(userRegistrationRequest.getEmail());
        String userId = UUID.randomUUID().toString();

        User registeredUser = userConverter.toUser(userRegistrationRequest, userId);
        registeredUser.setPassword(passwordEncoder.encode(registeredUser.getPassword()));

        userRepository.save(registeredUser);
        return new UserRegistrationResponse(userId);
    }

    @Transactional
    public UserDto update(String userId, UpdateUserRequest updateUserRequest) {
        User existingUser = findOrElseThrow(userId);

        User updatedUser = userConverter.toUser(updateUserRequest);
        updateUser(existingUser, updatedUser);

        return userConverter.toUserDto(existingUser);
    }

    private void updateUser(User destination, User source) {
        destination.setFirstName(source.getFirstName());
        destination.setLastName(source.getLastName());
        destination.setEmail(source.getEmail());
        destination.setPhoneNumber(source.getPhoneNumber());
        destination.setRole(source.getRole());
    }

    @Transactional
    public void changePassword(String userId, ChangePasswordRequest changePasswordRequest) {
        User user = findOrElseThrow(userId);

        validator.validatePasswordMatching(changePasswordRequest.getOldPassword(), user.getPassword());

        user.setPassword(passwordEncoder.encode(changePasswordRequest.getOldPassword()));
    }

    @Transactional
    public void deleteByUserId(String userId) {
        validator.validateUserPresence(userId);

        userRepository.deleteByUserId(userId);
    }

    private User findOrElseThrow(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(CANT_FIND_USER_ERROR.formatted(userId)));
    }
}
