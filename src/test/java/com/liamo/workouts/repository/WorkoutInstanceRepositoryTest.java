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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@ImportTestcontainers(PostgresTestContainer.class)
public class WorkoutInstanceRepositoryTest {

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

    @BeforeEach
    void beforeEach() {
        User bob = new User("bob@me.com", "Bob", "Martinez");
        user = userRepository.save(bob);
        workout = new Workout("Workout 1", "Description 1", user.getId());
        workout = workoutRepository.save(workout);
    }

    @AfterEach
    void afterEach() {
        exerciseInstanceRepository.deleteAll();
        workoutInstanceRepository.deleteAll();
        workoutRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void saveAndFindWorkoutInstance() {
        WorkoutInstance workoutInstance = new WorkoutInstance(workout.getId(), user.getId(), Instant.now(), Instant.now().plusSeconds(3600), WorkoutFeeling.GOOD, "Felt great!");
        workoutInstance = workoutInstanceRepository.save(workoutInstance);

        WorkoutInstance foundWorkoutInstance = workoutInstanceRepository.findById(workoutInstance.getId()).orElse(null);
        assertNotNull(foundWorkoutInstance);
        assertEquals(workoutInstance.getId(), foundWorkoutInstance.getId());
        assertEquals(workoutInstance.getWorkoutId(), foundWorkoutInstance.getWorkoutId());
        assertEquals(workoutInstance.getUserId(), foundWorkoutInstance.getUserId());
        assertEquals(workoutInstance.getStartTime(), foundWorkoutInstance.getStartTime());
        assertEquals(workoutInstance.getEndTime(), foundWorkoutInstance.getEndTime());
        assertEquals(workoutInstance.getFeeling(), foundWorkoutInstance.getFeeling());
        assertEquals(workoutInstance.getNotes(), foundWorkoutInstance.getNotes());
    }

    @Test
    @Transactional
    void addExerciseInstanceToWorkoutInstance() {
        WorkoutInstance workoutInstance = new WorkoutInstance(workout.getId(), user.getId(), Instant.now(), Instant.now().plusSeconds(3600), WorkoutFeeling.GOOD, "Felt great!");
        ExerciseInstance exerciseInstance = new ExerciseInstance(workoutInstance, user.getId(), ExerciseType.BENCH_PRESS, new WeightInfo(100, 10, 1), Instant.now());
        workoutInstance.addExerciseInstance(exerciseInstance);
        workoutInstance = workoutInstanceRepository.save(workoutInstance);

        WorkoutInstance foundWorkoutInstance = workoutInstanceRepository.findById(workoutInstance.getId()).orElse(null);
        assertNotNull(foundWorkoutInstance);
        assertEquals(1, foundWorkoutInstance.getExerciseInstances().size());
        assertTrue(foundWorkoutInstance.getExerciseInstances().contains(exerciseInstance));

        List<ExerciseInstance> exerciseInstances = exerciseInstanceRepository.findAll();
        assertEquals(1, exerciseInstances.size());
        assertEquals(exerciseInstance, exerciseInstances.getFirst());
    }

    @Test
    @Transactional
    void removeExerciseInstanceFromWorkoutInstance() {
        WorkoutInstance workoutInstance = new WorkoutInstance(workout.getId(), user.getId(), Instant.now(), Instant.now().plusSeconds(3600), WorkoutFeeling.GOOD, "Felt great!");
        ExerciseInstance exerciseInstance = new ExerciseInstance(workoutInstance, user.getId(), ExerciseType.BENCH_PRESS, new WeightInfo(100, 10, 1), Instant.now());
        workoutInstance.addExerciseInstance(exerciseInstance);
        workoutInstance = workoutInstanceRepository.save(workoutInstance);

        // We do not really ever need to call this
        workoutInstance.removeExerciseInstance(exerciseInstance);
        workoutInstance = workoutInstanceRepository.save(workoutInstance);

        WorkoutInstance foundWorkoutInstance = workoutInstanceRepository.findById(workoutInstance.getId()).orElse(null);
        assertNotNull(foundWorkoutInstance);
        assertEquals(0, foundWorkoutInstance.getExerciseInstances().size());

        List<ExerciseInstance> exerciseInstances = exerciseInstanceRepository.findAll();
        // We do not want removeExerciseInstance to ever delete an exercise instance
        assertEquals(1, exerciseInstances.size());
    }
}