package com.liamo.workouts.repository;

import com.liamo.workouts.PostgresTestContainer;
import com.liamo.workouts.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@ImportTestcontainers(PostgresTestContainer.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    User user;

    @BeforeEach
    void beforeEach() {
        User bob = new User("bob@me.com", "Bob", "Martinez");
        user = userRepository.save(bob);
    }

    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
    }

    @Test
    void findSingleUser() {
        Optional<User> repoUser = userRepository.findByEmail("bob@me.com");
        assertTrue(repoUser.isPresent());
        assertEquals(user, repoUser.get());
    }

    @Test
    void createNewUser_withSameEmail_throwException() {
        User newUser = new User("bob@me.com", "Bob2", "Martinez2");
        assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(newUser));
    }

}