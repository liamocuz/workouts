package com.liamo.workouts.entity;

import com.liamo.workouts.model.ExerciseType;
import com.liamo.workouts.model.WeightInfo;
import com.liamo.workouts.model.WorkoutFeeling;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class WorkoutInstanceTest {

    @Test
    void workoutInstanceCreation() {
        long workoutId = 1L;
        long userId = 2L;
        Instant startTime = Instant.now();
        Instant endTime = startTime.plusSeconds(3600);
        WorkoutFeeling feeling = WorkoutFeeling.GOOD;
        String notes = "Felt great";

        WorkoutInstance workoutInstance = new WorkoutInstance(workoutId, userId, startTime, endTime, feeling, notes);

        assertEquals(workoutId, workoutInstance.getWorkoutId());
        assertEquals(userId, workoutInstance.getUserId());
        assertEquals(startTime, workoutInstance.getStartTime());
        assertEquals(endTime, workoutInstance.getEndTime());
        assertEquals(feeling, workoutInstance.getFeeling());
        assertEquals(notes, workoutInstance.getNotes());
        assertEquals(new HashSet<>(), workoutInstance.getExerciseInstances());
    }

    @Test
    void workoutInstanceEquality() {
        long workoutId = 1L;
        long userId = 2L;
        Instant startTime = Instant.now();
        Instant endTime = startTime.plusSeconds(3600);

        WorkoutInstance workoutInstance1 = new WorkoutInstance(workoutId, userId, startTime, endTime, WorkoutFeeling.GOOD, "Felt great");
        WorkoutInstance workoutInstance2 = new WorkoutInstance(workoutId, userId, startTime, endTime, WorkoutFeeling.GOOD, "Felt great");

        assertEquals(workoutInstance1, workoutInstance2);
        assertEquals(workoutInstance1.hashCode(), workoutInstance2.hashCode());
    }

    @Test
    void workoutInstanceInequality() {
        long workoutId1 = 1L;
        long workoutId2 = 2L;
        long userId = 2L;
        Instant startTime = Instant.now();
        Instant endTime = startTime.plusSeconds(3600);

        WorkoutInstance workoutInstance1 = new WorkoutInstance(workoutId1, userId, startTime, endTime, WorkoutFeeling.GOOD, "Felt great");
        WorkoutInstance workoutInstance2 = new WorkoutInstance(workoutId2, userId, startTime, endTime, WorkoutFeeling.GOOD, "Felt great");

        assertNotEquals(workoutInstance1, workoutInstance2);
        assertNotEquals(workoutInstance1.hashCode(), workoutInstance2.hashCode());
    }

    @Test
    void addAndRemoveExerciseInstance() {
        WorkoutInstance workoutInstance = new WorkoutInstance(1L, 2L, Instant.now(), Instant.now().plusSeconds(3600), WorkoutFeeling.GOOD, "Felt great");
        ExerciseType type = ExerciseType.BENCH_PRESS;
        WeightInfo weightInfo = new WeightInfo(100, 10, 1);
        Instant createdAt = Instant.now();
        ExerciseInstance exerciseInstance = new ExerciseInstance(workoutInstance, 2L, type, weightInfo, createdAt);

        workoutInstance.addExerciseInstance(exerciseInstance);
        assertTrue(workoutInstance.getExerciseInstances().contains(exerciseInstance));
        assertEquals(workoutInstance, exerciseInstance.getWorkoutInstance());

        workoutInstance.removeExerciseInstance(exerciseInstance);
        assertFalse(workoutInstance.getExerciseInstances().contains(exerciseInstance));
        assertNull(exerciseInstance.getWorkoutInstance());
    }
}