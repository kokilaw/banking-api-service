package io.kokilaw.banking.controller;

import io.kokilaw.banking.dto.AccountDTO;
import io.kokilaw.banking.dto.UserDTO;
import io.kokilaw.banking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by kokilaw on 2022-08-09
 */
@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDTO createUser(@Valid @RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @GetMapping("/{userId}")
    public UserDTO getUser(@PathVariable long userId) {
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    public UserDTO updateAccount(@PathVariable long userId, @Valid @RequestBody UserDTO userDTO) {
        return userService.updateUser(userId, userDTO);
    }

    @DeleteMapping("/{userId}")
    public void deleteAccount(@PathVariable long userId) {
        userService.deleteUser(userId);
    }


}
