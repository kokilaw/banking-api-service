package io.kokilaw.banking.service.impl;

import io.kokilaw.banking.dto.UserDTO;
import io.kokilaw.banking.repository.UserRepository;
import io.kokilaw.banking.repository.model.User;
import io.kokilaw.banking.service.UserService;
import io.kokilaw.banking.util.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static io.kokilaw.banking.service.impl.Helper.getUserDTO;

/**
 * Created by kokilaw on 2022-08-11
 */
@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CommonService commonService;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("New user is getting created")
    public void newUserIsGettingCreated() {

        UserDTO userDTO = getUserDTO();

        Mockito.when(userRepository.save(UserMapper.mapToUser(userDTO))).thenReturn(UserMapper.mapToUser(userDTO));

        UserDTO createdUser = userService.createUser(userDTO);
        Mockito.verify(userRepository, Mockito.times(1)).save(UserMapper.mapToUser(userDTO));

        Assertions.assertEquals(userDTO.getEmail(), createdUser.getEmail());
        Assertions.assertEquals(userDTO.getDateOfBirth(), createdUser.getDateOfBirth());
        Assertions.assertEquals(userDTO.getNic(), createdUser.getNic());

    }

    @Test
    @DisplayName("When existing user is requested")
    public void whenExistingUserIsRequested() {

        UserDTO userDTO = getUserDTO();

        User user = UserMapper.mapToUser(userDTO);

        Mockito.when(commonService.getUserIfAvailable(1L)).thenReturn(user);

        UserDTO returnUser = userService.getUser(1L);
        Mockito.verify(commonService, Mockito.times(1)).getUserIfAvailable(1L);

        Assertions.assertEquals(userDTO.getEmail(), returnUser.getEmail());
        Assertions.assertEquals(userDTO.getDateOfBirth(), returnUser.getDateOfBirth());
        Assertions.assertEquals(userDTO.getNic(), returnUser.getNic());


    }

    @Test
    @DisplayName("When existing user is updated")
    public void whenExistingUserIsUpdated() {

        UserDTO userDTO = getUserDTO();
        User user = UserMapper.mapToUser(userDTO);

        UserDTO updatedUserDTO = getUserDTO();
        updatedUserDTO.setGivenName("Sam");
        User updatedUser = UserMapper.mapToUser(updatedUserDTO);

        Mockito.when(commonService.getUserIfAvailable(1L)).thenReturn(user);
        Mockito.when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        UserDTO returnedUserDTO = userService.updateUser(1L, updatedUserDTO);
        Mockito.verify(commonService, Mockito.times(1)).getUserIfAvailable(1L);
        Mockito.verify(userRepository, Mockito.times(1)).save(updatedUser);

        Assertions.assertEquals(userDTO.getEmail(), returnedUserDTO.getEmail());
        Assertions.assertEquals("Sam", returnedUserDTO.getGivenName());


    }

}
