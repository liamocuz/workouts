package com.liamo.workouts.entity;

import com.liamo.workouts.model.ExerciseType;
import com.liamo.workouts.model.WeightInfo;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class ExerciseTest {

    @Test
    void exerciseCreation() {
        Workout workout = new Workout();
        workout.setName("Morning Workout");
        ExerciseType type = ExerciseType.BENCH_PRESS;
        WeightInfo weightInfo = new WeightInfo(100, 10, 1);
        Instant now = Instant.now();

        Exercise exercise = new Exercise(workout, type, weightInfo);
        exercise.setCreatedAt(now);
        exercise.setUpdatedAt(now);

        assertEquals(workout, exercise.getWorkout());
        assertEquals(type, exercise.getType());
        assertEquals(weightInfo, exercise.getWeightInfo());
        assertEquals(now, exercise.getCreatedAt());
        assertEquals(now, exercise.getUpdatedAt());
    }

    @Test
    void exerciseEquality() {
        Workout workout = new Workout();
        ExerciseType type = ExerciseType.BENCH_PRESS;
        WeightInfo weightInfo = new WeightInfo(100, 10, 1);

        Exercise exercise1 = new Exercise(workout, type, weightInfo);
        Exercise exercise2 = new Exercise(workout, type, weightInfo);

        assertEquals(exercise1, exercise2);
        assertEquals(exercise1.hashCode(), exercise2.hashCode());
    }

    @Test
    void exerciseInequality() {
        Workout workout = new Workout();
        ExerciseType type1 = ExerciseType.BENCH_PRESS;
        ExerciseType type2 = ExerciseType.SQUAT;
        WeightInfo weightInfo1 = new WeightInfo(100, 10, 1);
        WeightInfo weightInfo2 = new WeightInfo(150, 8, 4);

        Exercise exercise1 = new Exercise(workout, type1, weightInfo1);
        Exercise exercise2 = new Exercise(workout, type2, weightInfo2);

        assertNotEquals(exercise1, exercise2);
        assertNotEquals(exercise1.hashCode(), exercise2.hashCode());
    }
}