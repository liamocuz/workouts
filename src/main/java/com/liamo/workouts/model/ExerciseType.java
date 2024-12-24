package com.liamo.workouts.model;

public enum ExerciseType {
    BENCH_PRESS(MuscleGroup.CHEST, "Bench Press"),
    INCLINE_BENCH_PRESS(MuscleGroup.CHEST, "Incline Bench Press"),
    DUMBBELL_FLY(MuscleGroup.CHEST, "Dumbbell Fly"),

    PULL_UP(MuscleGroup.BACK, "Pull Up"),
    DEADLIFT(MuscleGroup.BACK, "Deadlift"),
    BENT_OVER_ROW(MuscleGroup.BACK, "Bent Over Row"),

    BICEP_CURL(MuscleGroup.ARMS, "Bicep Curl"),
    TRICEP_EXTENSION(MuscleGroup.ARMS, "Tricep Extension"),

    SQUAT(MuscleGroup.LEGS, "Squat"),
    LUNGES(MuscleGroup.LEGS, "Lunges"),
    CALF_RAISE(MuscleGroup.LEGS, "Calf Raise"),
    LEG_PRESS(MuscleGroup.LEGS, "Leg Press"),

    SHOULDER_PRESS(MuscleGroup.SHOULDERS, "Shoulder Press"),
    LATERAL_RAISE(MuscleGroup.SHOULDERS, "Lateral Raise"),
    FRONT_RAISE(MuscleGroup.SHOULDERS, "Front Raise"),

    CRUNCH(MuscleGroup.CORE, "Crunch"),
    RUSSIAN_TWIST(MuscleGroup.CORE, "Russian Twist"),
    PLANK(MuscleGroup.CORE, "Plank"),
    SUPERMAN(MuscleGroup.CORE, "Superman")
    ;

    private final MuscleGroup muscleGroup;
    private final String name;

    ExerciseType(MuscleGroup muscleGroup, String name) {
        this.muscleGroup = muscleGroup;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public MuscleGroup getMuscleGroup() {
        return muscleGroup;
    }
}
