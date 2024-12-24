package com.liamo.workouts.entity;

import com.liamo.workouts.model.ExerciseType;
import com.liamo.workouts.model.WeightInfo;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "exercise")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(
        fetch = FetchType.LAZY,
        cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}
    )
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout workout;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ExerciseType type;

    @Embedded
    private WeightInfo weightInfo;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp(source = SourceType.DB)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp(source = SourceType.DB)
    private Instant updatedAt;

    public Exercise() {}

    public Exercise(Workout workout, ExerciseType type, WeightInfo weightInfo) {
        this.workout = workout;
        this.type = type;
        this.weightInfo = weightInfo;
    }

    @Override
    public String toString() {
        return "Exercise{" +
            "id=" + id +
            ", workout=" + workout.getName() +
            ", type=" + type +
            ", weightInfo=" + weightInfo +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Exercise exercise = (Exercise) o;
        return type == exercise.type && Objects.equals(weightInfo, exercise.weightInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, weightInfo);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public ExerciseType getType() {
        return type;
    }

    public void setType(ExerciseType type) {
        this.type = type;
    }

    public WeightInfo getWeightInfo() {
        return weightInfo;
    }

    public void setWeightInfo(WeightInfo weightInfo) {
        this.weightInfo = weightInfo;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
