package io.kokilaw.banking.service;

import io.kokilaw.banking.dto.UserDTO;

/**
 * Created by kokilaw on 2022-08-11
 */
public interface UserService {

    UserDTO createUser(UserDTO userDTO);

    UserDTO getUser(long userId);

    UserDTO updateUser(long userId, UserDTO userDTO);

    void deleteUser(long userId);

}
