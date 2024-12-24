package com.liamo.workouts.repository;

import com.liamo.workouts.entity.WorkoutInstance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutInstanceRepository extends JpaRepository<WorkoutInstance, Long> {
}
