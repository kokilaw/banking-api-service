package io.kokilaw.banking.repository;

import io.kokilaw.banking.repository.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static io.kokilaw.banking.repository.Helper.getUser;


/**
 * Created by kokilaw on 2022-08-12
 */

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("When an user is saved, saved user is returned")
    public void whenUserIsSaved_ReturnSavedUser() {

        User user = getUser();
        User initialSavedUser = userRepository.save(user);

        Optional<User> savedUserOptional = userRepository.findById(initialSavedUser.getId());
        Assertions.assertTrue(savedUserOptional.isPresent());

        savedUserOptional.ifPresent(savedUser -> {
            Assertions.assertEquals(initialSavedUser.getId(), savedUser.getId());
            Assertions.assertEquals(initialSavedUser.getNic(), savedUser.getNic());
            Assertions.assertEquals(initialSavedUser.getEmail(), savedUser.getEmail());
        });

    }

    @Test
    @DisplayName("When an existing user is updated")
    public void whenAnExistingUserIsUpdated() {

        User user = getUser();
        user.setEmail("sample@gmail.com");
        user.setNic("964285094v");
        User initialSavedUser = userRepository.save(user);

        Optional<User> savedUserOptional = userRepository.findById(initialSavedUser.getId());
        Assertions.assertTrue(savedUserOptional.isPresent());

        String updatingNic = "964285194v";
        savedUserOptional.ifPresent(savedUser -> {
            savedUser.setNic(updatingNic);
            userRepository.save(savedUser);
        });

        Optional<User> updatedUserOptional = userRepository.findById(initialSavedUser.getId());
        Assertions.assertTrue(updatedUserOptional.isPresent());

        updatedUserOptional.ifPresent(updatedUser -> {
            Assertions.assertEquals(updatingNic, updatedUser.getNic());
        });

    }

    @Test
    @DisplayName("When an existing user is deleted")
    public void whenAnExistingUserIsDeleted() {

        User user = getUser();
        user.setEmail("sammple@gmail.com");
        user.setNic("964285094v");
        User initialSavedUser = userRepository.save(user);

        Optional<User> savedUserOptional = userRepository.findById(initialSavedUser.getId());
        Assertions.assertTrue(savedUserOptional.isPresent());

        savedUserOptional.ifPresent(savedUser -> {
            userRepository.deleteById(initialSavedUser.getId());
        });

        Optional<User> updatedUserOptional = userRepository.findById(initialSavedUser.getId());
        Assertions.assertFalse(updatedUserOptional.isPresent());

    }

}
