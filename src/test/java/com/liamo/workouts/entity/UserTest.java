package com.liamo.workouts.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void userCreation() {
        User user = new User();
        user.setId(1L);
        user.setEmail("testuser@example.com");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setVerified(true);
        user.setWorkoutStreak(5);
        user.setUsesMetric(true);

        assertEquals(1L, user.getId());
        assertEquals("testuser@example.com", user.getEmail());
        assertEquals("Test", user.getFirstName());
        assertEquals("User", user.getLastName());
        assertTrue(user.isVerified());
        assertEquals(5, user.getWorkoutStreak());
        assertTrue(user.isUsesMetric());
    }

    @Test
    void userEquality() {
        User user1 = new User();
        user1.setEmail("testuser@example.com");

        User user2 = new User();
        user2.setEmail("testuser@example.com");

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void userInequality() {
        User user1 = new User();
        user1.setEmail("testuser@example.com");

        User user2 = new User();
        user2.setEmail("anotheruser@example.com");

        assertNotEquals(user1, user2);
        assertNotEquals(user1.hashCode(), user2.hashCode());
    }
}