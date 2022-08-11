package io.kokilaw.banking.util.mapper;

import io.kokilaw.banking.dto.UserDTO;
import io.kokilaw.banking.repository.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by kokilaw on 2022-08-11
 */
public class UserMapper {

    private UserMapper() {

    }

    public static User mapToUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setGivenName(userDTO.getGivenName());
        user.setFamilyName(userDTO.getFamilyName());
        user.setDateOfBirth(LocalDate.parse(userDTO.getDateOfBirth()));
        user.setNic(userDTO.getNic());
        return user;
    }

    public static UserDTO mapToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setGivenName(user.getGivenName());
        userDTO.setFamilyName(user.getFamilyName());
        userDTO.setDateOfBirth(user.getDateOfBirth().format(DateTimeFormatter.ISO_LOCAL_DATE));
        userDTO.setNic(user.getNic());
        return userDTO;
    }

}
