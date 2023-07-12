package dataart.workshop.controller;

import dataart.workshop.dto.ChangePasswordRequest;
import dataart.workshop.dto.PaginatedUserDto;
import dataart.workshop.dto.UpdateUserRequest;
import dataart.workshop.dto.UserDto;
import dataart.workshop.dto.UserRegistrationRequest;
import dataart.workshop.dto.UserRegistrationResponse;
import dataart.workshop.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public PaginatedUserDto getAll(@RequestParam(required = false) Integer page,
                                   @RequestParam(required = false) Integer size) {
        return userService.findAll(page, size);
    }

    @GetMapping("/{userId}")
    public UserDto getByUserId(@PathVariable String userId) {
        return userService.findByUserId(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserRegistrationResponse register(@RequestBody @Valid UserRegistrationRequest userRegistrationRequest) {
        return userService.register(userRegistrationRequest);
    }

    @PutMapping("/{userId}")
    public UserDto update(@PathVariable String userId,
                          @RequestBody @Valid UpdateUserRequest updateUserRequest) {
        return userService.update(userId, updateUserRequest);
    }

    @PatchMapping("/{userId}")
    public void changePassword(@PathVariable String userId,
                               @RequestBody ChangePasswordRequest changePasswordRequest) {
        userService.changePassword(userId, changePasswordRequest);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable String userId) {
        userService.deleteByUserId(userId);
    }
}
