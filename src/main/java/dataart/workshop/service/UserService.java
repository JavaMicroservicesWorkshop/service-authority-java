package dataart.workshop.service;

import dataart.workshop.converter.UserConverter;
import dataart.workshop.domain.User;
import dataart.workshop.dto.ChangePasswordRequest;
import dataart.workshop.dto.PaginatedUserDto;
import dataart.workshop.dto.Role;
import dataart.workshop.dto.UpdateUserRequest;
import dataart.workshop.dto.UserDto;
import dataart.workshop.dto.UserRegistrationRequest;
import dataart.workshop.dto.UserRegistrationResponse;
import dataart.workshop.exception.UserNotFoundException;
import dataart.workshop.repository.UserRepository;
import dataart.workshop.utils.PageUtils;
import dataart.workshop.validator.UserValidator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger logger = Logger.getLogger(UserService.class.getName());
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
        logger.info("New user registered. User is: " + registeredUser.getUserId());

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

        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));

        logger.info("Password changed successfully");
    }

    @Transactional
    public void deleteByUserId(String userId) {
        validator.validateUserPresence(userId);

        userRepository.deleteByUserId(userId);

        logger.info("User " + userId + " is deleted");
    }

    private User findOrElseThrow(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(CANT_FIND_USER_ERROR.formatted(userId)));
    }

    @PostConstruct
    private void postConstruct() {
        User admin = createDefaultAdmin();

        userRepository.save(admin);
    }

    private User createDefaultAdmin() {
        User admin = new User();
        admin.setUserId(UUID.randomUUID().toString());
        admin.setEmail("admin101@dataart.com");
        admin.setPassword(passwordEncoder.encode("password"));
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        admin.setPhoneNumber("+380509876543");
        admin.setRole(Role.ADMIN);
        return admin;
    }
}
