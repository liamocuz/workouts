package com.liamo.workouts.repository;

import com.liamo.workouts.PostgresTestContainer;
import com.liamo.workouts.entity.Exercise;
import com.liamo.workouts.entity.User;
import com.liamo.workouts.entity.Workout;
import com.liamo.workouts.model.ExerciseType;
import com.liamo.workouts.model.WeightInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@ImportTestcontainers(PostgresTestContainer.class)
public class WorkoutRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    WorkoutRepository workoutRepository;
    // This is purely for testing as we don't really want to use the ExerciseRepository in the application
    @Autowired
    ExerciseRepository exerciseRepository;

    User user;

    @BeforeEach
    void beforeEach() {
        User bob = new User("bob@me.com", "Bob", "Martinez");
        user = userRepository.save(bob);
    }

    @AfterEach
    void afterEach() {
        workoutRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findAllByUserId() {
        Workout workout1 = new Workout("Workout 1", "Description 1", user.getId());
        Workout workout2 = new Workout("Workout 2", "Description 2", user.getId());
        workout1 = workoutRepository.save(workout1);
        workout2 = workoutRepository.save(workout2);

        List<Workout> workouts = workoutRepository.findAllByUserId(user.getId());
        assertEquals(2, workouts.size());
        assertTrue(workouts.contains(workout1));
        assertTrue(workouts.contains(workout2));
    }

    @Test
    void saveAndFindWorkout() {
        Workout workout = new Workout("Workout 1", "Description 1", user.getId());
        workout = workoutRepository.save(workout);

        Workout foundWorkout = workoutRepository.findById(workout.getId()).orElse(null);
        assertNotNull(foundWorkout);
        assertEquals(workout.getId(), foundWorkout.getId());
        assertEquals(workout.getName(), foundWorkout.getName());
        assertEquals(workout.getDescription(), foundWorkout.getDescription());
        assertEquals(workout.getUserId(), foundWorkout.getUserId());
    }

    @Test
    @Transactional
    void addExerciseToWorkout() {
        Workout workout = new Workout("Workout 1", "Description 1", user.getId());
        Exercise exercise = new Exercise(workout, ExerciseType.BENCH_PRESS, new WeightInfo(100, 10, 1));
        workout.addExercise(exercise);
        workout = workoutRepository.save(workout);

        Workout foundWorkout = workoutRepository.findById(workout.getId()).orElse(null);
        assertNotNull(foundWorkout);
        assertEquals(1, foundWorkout.getExercises().size());
        assertTrue(foundWorkout.getExercises().contains(exercise));

        List<Exercise> exercises = exerciseRepository.findAll();
        assertEquals(1, exercises.size());
        assertEquals(exercise, exercises.getFirst());
    }


    @Test
    @Transactional
    void removeExerciseFromWorkout() {
        Workout workout = new Workout("Workout 1", "Description 1", user.getId());
        Exercise exercise = new Exercise(workout, ExerciseType.BENCH_PRESS, new WeightInfo(100, 10, 1));
        workout.addExercise(exercise);
        workout = workoutRepository.save(workout);

        workout.removeExercise(exercise);
        workout = workoutRepository.save(workout);

        Workout foundWorkout = workoutRepository.findById(workout.getId()).orElse(null);
        assertNotNull(foundWorkout);
        assertEquals(0, foundWorkout.getExercises().size());

        List<Exercise> exercises = exerciseRepository.findAll();
        assertEquals(0, exercises.size());
    }
}