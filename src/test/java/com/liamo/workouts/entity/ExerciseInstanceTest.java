package com.liamo.workouts.entity;

import com.liamo.workouts.model.ExerciseType;
import com.liamo.workouts.model.WeightInfo;
import com.liamo.workouts.model.WorkoutFeeling;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class ExerciseInstanceTest {

    @Test
    void exerciseInstanceCreation() {
        WorkoutInstance workoutInstance = new WorkoutInstance(1L, 2L, Instant.now(), Instant.now().plusSeconds(3600), WorkoutFeeling.GOOD, "Felt great");
        long userId = 2L;
        ExerciseType type = ExerciseType.BENCH_PRESS;
        WeightInfo weightInfo = new WeightInfo(100, 10, 1);
        Instant createdAt = Instant.now();

        ExerciseInstance exerciseInstance = new ExerciseInstance(workoutInstance, userId, type, weightInfo, createdAt);

        assertEquals(workoutInstance, exerciseInstance.getWorkoutInstance());
        assertEquals(userId, exerciseInstance.getUserId());
        assertEquals(type, exerciseInstance.getType());
        assertEquals(weightInfo, exerciseInstance.getWeightInfo());
        assertEquals(createdAt, exerciseInstance.getCreatedAt());
    }

    @Test
    void exerciseInstanceEquality() {
        WorkoutInstance workoutInstance = new WorkoutInstance(1L, 2L, Instant.now(), Instant.now().plusSeconds(3600), WorkoutFeeling.GOOD, "Felt great");
        long userId = 2L;
        ExerciseType type = ExerciseType.BENCH_PRESS;
        WeightInfo weightInfo = new WeightInfo(100, 10, 1);
        Instant createdAt = Instant.now();

        ExerciseInstance exerciseInstance1 = new ExerciseInstance(workoutInstance, userId, type, weightInfo, createdAt);
        ExerciseInstance exerciseInstance2 = new ExerciseInstance(workoutInstance, userId, type, weightInfo, createdAt);

        assertEquals(exerciseInstance1, exerciseInstance2);
        assertEquals(exerciseInstance1.hashCode(), exerciseInstance2.hashCode());
    }

    @Test
    void exerciseInstanceInequality() {
        WorkoutInstance workoutInstance1 = new WorkoutInstance(1L, 2L, Instant.now(), Instant.now().plusSeconds(3600), WorkoutFeeling.GOOD, "Felt great");
        WorkoutInstance workoutInstance2 = new WorkoutInstance(2L, 3L, Instant.now(), Instant.now().plusSeconds(3600), WorkoutFeeling.GOOD, "Felt great");
        long userId1 = 2L;
        long userId2 = 3L;
        ExerciseType type1 = ExerciseType.BENCH_PRESS;
        ExerciseType type2 = ExerciseType.SQUAT;
        WeightInfo weightInfo1 = new WeightInfo(100, 10, 1);
        WeightInfo weightInfo2 = new WeightInfo(200, 5, 2);
        Instant createdAt1 = Instant.now();
        Instant createdAt2 = Instant.now().plusSeconds(3600);

        ExerciseInstance exerciseInstance1 = new ExerciseInstance(workoutInstance1, userId1, type1, weightInfo1, createdAt1);
        ExerciseInstance exerciseInstance2 = new ExerciseInstance(workoutInstance2, userId2, type2, weightInfo2, createdAt2);

        assertNotEquals(exerciseInstance1, exerciseInstance2);
        assertNotEquals(exerciseInstance1.hashCode(), exerciseInstance2.hashCode());
    }
}