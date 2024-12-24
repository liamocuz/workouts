package com.liamo.workouts.entity;

import com.liamo.workouts.model.ExerciseType;
import com.liamo.workouts.model.WeightInfo;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class WorkoutTest {

    @Test
    void workoutCreation() {
        String name = "Morning Workout";
        String description = "Full body workout";
        long userId = 1L;
        Instant now = Instant.now();

        Workout workout = new Workout(name, description, userId);
        workout.setCreatedAt(now);
        workout.setUpdatedAt(now);

        assertEquals(name, workout.getName());
        assertEquals(description, workout.getDescription());
        assertEquals(userId, workout.getUserId());
        assertEquals(now, workout.getCreatedAt());
        assertEquals(now, workout.getUpdatedAt());
        assertFalse(workout.isArchived());
        assertEquals(new HashSet<>(), workout.getExercises());
    }

    @Test
    void workoutEquality() {
        String name = "Morning Workout";
        String description = "Full body workout";
        long userId = 1L;

        Workout workout1 = new Workout(name, description, userId);
        Workout workout2 = new Workout(name, description, userId);

        assertEquals(workout1, workout2);
        assertEquals(workout1.hashCode(), workout2.hashCode());
    }

    @Test
    void workoutInequality() {
        String name1 = "Morning Workout";
        String name2 = "Evening Workout";
        String description = "Full body workout";
        long userId = 1L;

        Workout workout1 = new Workout(name1, description, userId);
        Workout workout2 = new Workout(name2, description, userId);

        assertNotEquals(workout1, workout2);
        assertNotEquals(workout1.hashCode(), workout2.hashCode());
    }

    @Test
    void addAndRemoveExercise() {
        Workout workout = new Workout("Morning Workout", "Full body workout", 1L);
        ExerciseType type = ExerciseType.BENCH_PRESS;
        WeightInfo weightInfo = new WeightInfo(100, 10, 1);
        Exercise exercise = new Exercise(workout, type, weightInfo);

        workout.addExercise(exercise);
        assertTrue(workout.getExercises().contains(exercise));
        assertEquals(workout, exercise.getWorkout());

        workout.removeExercise(exercise);
        assertFalse(workout.getExercises().contains(exercise));
        assertNull(exercise.getWorkout());
    }
}