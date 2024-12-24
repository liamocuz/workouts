package com.liamo.workouts.repository;

import com.liamo.workouts.entity.ExerciseInstance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseInstanceRepository extends JpaRepository<ExerciseInstance, Long> {
}
