package com.liamo.workouts.repository;

import com.liamo.workouts.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This repository shouldn't really be used in the application code.
 * This is because we want to manage workouts and their relationship to exercises from the
 * workout side. So that is why we can use orphanRemoval = true in the Workout entity alongside
 * the helper methods to add or remove an exercise.
 * It can be used for testing purposes.
 */
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}
