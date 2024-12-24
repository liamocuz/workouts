package com.liamo.workouts.repository;

import com.liamo.workouts.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    List<Workout> findAllByUserId(long userId);
}
