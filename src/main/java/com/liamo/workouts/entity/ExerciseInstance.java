package com.liamo.workouts.entity;

import com.liamo.workouts.model.ExerciseType;
import com.liamo.workouts.model.WeightInfo;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "exercise_instance")
public class ExerciseInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(
        fetch = FetchType.LAZY,
        cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}
    )
    @JoinColumn(name = "workout_instance_id", nullable = false)
    private WorkoutInstance workoutInstance;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ExerciseType type;

    @Embedded
    private WeightInfo weightInfo;

    // Not managed by the DB but instead by the user's platform
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public ExerciseInstance() {}

    public ExerciseInstance(WorkoutInstance workoutInstance, long userId, ExerciseType type, WeightInfo weightInfo, Instant createdAt) {
        this.workoutInstance = workoutInstance;
        this.userId = userId;
        this.type = type;
        this.weightInfo = weightInfo;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ExerciseInstance{" +
            "id=" + id +
            ", workoutInstance=" + workoutInstance +
            ", userId=" + userId +
            ", type=" + type +
            ", weightInfo=" + weightInfo +
            ", createdAt=" + createdAt +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ExerciseInstance that = (ExerciseInstance) o;
        return userId == that.userId && Objects.equals(workoutInstance, that.workoutInstance) && type == that.type && Objects.equals(weightInfo, that.weightInfo) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workoutInstance, userId, type, weightInfo, createdAt);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkoutInstance getWorkoutInstance() {
        return workoutInstance;
    }

    public void setWorkoutInstance(WorkoutInstance workoutInstance) {
        this.workoutInstance = workoutInstance;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
}
