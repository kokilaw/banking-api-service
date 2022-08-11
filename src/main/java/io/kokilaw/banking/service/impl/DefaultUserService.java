package io.kokilaw.banking.service.impl;

import io.kokilaw.banking.dto.UserDTO;
import io.kokilaw.banking.repository.UserRepository;
import io.kokilaw.banking.repository.model.User;
import io.kokilaw.banking.service.UserService;
import io.kokilaw.banking.util.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by kokilaw on 2022-08-11
 */
@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final CommonService commonService;


    @Autowired
    public DefaultUserService(UserRepository userRepository, CommonService commonService) {
        this.userRepository = userRepository;
        this.commonService = commonService;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = UserMapper.mapToUser(userDTO);
        return UserMapper.mapToUserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO getUser(long userId) {
        User user = commonService.getUserIfAvailable(userId);
        return UserMapper.mapToUserDTO(user);
    }

    @Override
    public UserDTO updateUser(long userId, UserDTO userDTO) {

        User currentUser = commonService.getUserIfAvailable(userId);
        User updateUser = UserMapper.mapToUser(userDTO);

        currentUser.setNic(updateUser.getNic());
        currentUser.setEmail(updateUser.getEmail());
        currentUser.setGivenName(updateUser.getGivenName());
        currentUser.setFamilyName(updateUser.getFamilyName());
        currentUser.setDateOfBirth(updateUser.getDateOfBirth());

        return UserMapper.mapToUserDTO(userRepository.save(currentUser));
    }

    @Override
    public void deleteUser(long userId) {
        userRepository.delete(commonService.getUserIfAvailable(userId));
    }

}
