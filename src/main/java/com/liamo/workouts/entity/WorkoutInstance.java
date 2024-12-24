package com.liamo.workouts.entity;

import com.liamo.workouts.model.WorkoutFeeling;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "workout_instance")
public class WorkoutInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Maintain the reference to the workout that this instance is based off of
    // That workout will never be deleted, so we can safely reference it here
    @Column(name = "workout_id", nullable = false)
    private long workoutId;

    @Column(name = "user_id", nullable = false)
    private long userId;

    // Not managed by the DB but instead by the user's platform
    @Column(name = "start_time", nullable = false)
    private Instant startTime;

    // Not managed by the DB but instead by the user's platform
    @Column(name = "end_time", nullable = false)
    private Instant endTime;

    @Column(name = "feeling")
    @Enumerated(EnumType.STRING)
    private WorkoutFeeling feeling;

    @Column(name = "notes")
    private String notes;

    // No orphanRemoval because we never really want to delete anything
    @OneToMany(
        mappedBy = "workoutInstance",
        fetch = FetchType.LAZY,
        cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}
    )
    private Set<ExerciseInstance> exerciseInstances = new HashSet<>();

    public WorkoutInstance() {}

    public WorkoutInstance(long workoutId, long userId, Instant startTime, Instant endTime, WorkoutFeeling feeling, String notes) {
        this.workoutId = workoutId;
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.feeling = feeling;
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "WorkoutInstance{" +
            "id=" + id +
            ", workoutId=" + workoutId +
            ", userId=" + userId +
            ", startTime=" + startTime +
            ", endTime=" + endTime +
            ", feeling=" + feeling +
            ", notes='" + notes + '\'' +
            ", exerciseInstances=" + exerciseInstances.size() +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        WorkoutInstance that = (WorkoutInstance) o;
        return workoutId == that.workoutId && userId == that.userId && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workoutId, userId, startTime, endTime);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(long workoutId) {
        this.workoutId = workoutId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public WorkoutFeeling getFeeling() {
        return feeling;
    }

    public void setFeeling(WorkoutFeeling feeling) {
        this.feeling = feeling;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<ExerciseInstance> getExerciseInstances() {
        return exerciseInstances;
    }

    public void setExerciseInstances(Set<ExerciseInstance> exerciseInstances) {
        this.exerciseInstances = exerciseInstances;
    }

    public void addExerciseInstance(ExerciseInstance exerciseInstance) {
        exerciseInstances.add(exerciseInstance);
        exerciseInstance.setWorkoutInstance(this);
    }

    /**
     * This method should never really be called as we don't want to delete anything
     */
    public void removeExerciseInstance(ExerciseInstance exerciseInstance) {
        exerciseInstances.remove(exerciseInstance);
        exerciseInstance.setWorkoutInstance(null);
    }
}
