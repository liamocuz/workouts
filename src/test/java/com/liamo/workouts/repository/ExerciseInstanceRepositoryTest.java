package com.liamo.workouts.repository;

import com.liamo.workouts.PostgresTestContainer;
import com.liamo.workouts.entity.*;
import com.liamo.workouts.model.ExerciseType;
import com.liamo.workouts.model.WeightInfo;
import com.liamo.workouts.model.WorkoutFeeling;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The ExerciseInstanceRepository will mainly be used to fetch ExerciseInstances by things
 * like workout instance, time range, etc.
 */
@SpringBootTest
@TestPropertySource("/application-test.properties")
@ImportTestcontainers(PostgresTestContainer.class)
public class ExerciseInstanceRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    WorkoutRepository workoutRepository;
    @Autowired
    WorkoutInstanceRepository workoutInstanceRepository;
    @Autowired
    ExerciseInstanceRepository exerciseInstanceRepository;

    User user;
    Workout workout;
    WorkoutInstance workoutInstance;

    @BeforeEach
    void beforeEach() {
        User bob = new User("bob@me.com", "Bob", "Martinez");
        user = userRepository.save(bob);
        workout = new Workout("Workout 1", "Description 1", user.getId());
        workout = workoutRepository.save(workout);
        workoutInstance = new WorkoutInstance(workout.getId(), user.getId(), Instant.now(), Instant.now().plusSeconds(3600), WorkoutFeeling.GOOD, "Felt great!");
        workoutInstance = workoutInstanceRepository.save(workoutInstance);
    }

    @AfterEach
    void afterEach() {
        exerciseInstanceRepository.deleteAll();
        workoutInstanceRepository.deleteAll();
        workoutRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @Transactional
    void saveAndFindExerciseInstance() {
        ExerciseInstance exerciseInstance = new ExerciseInstance(workoutInstance, user.getId(), ExerciseType.BENCH_PRESS, new WeightInfo(100, 10, 1), Instant.now());
        exerciseInstance = exerciseInstanceRepository.save(exerciseInstance);

        ExerciseInstance foundExerciseInstance = exerciseInstanceRepository.findById(exerciseInstance.getId()).orElse(null);
        assertNotNull(foundExerciseInstance);
        assertEquals(exerciseInstance.getId(), foundExerciseInstance.getId());
        assertEquals(exerciseInstance.getWorkoutInstance().getId(), foundExerciseInstance.getWorkoutInstance().getId());
        assertEquals(exerciseInstance.getUserId(), foundExerciseInstance.getUserId());
        assertEquals(exerciseInstance.getType(), foundExerciseInstance.getType());
        assertEquals(exerciseInstance.getWeightInfo(), foundExerciseInstance.getWeightInfo());
        assertEquals(exerciseInstance.getCreatedAt(), foundExerciseInstance.getCreatedAt());
    }
}