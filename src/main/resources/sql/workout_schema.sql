-- We use singular naming in this house
-- The workout configuration table
CREATE TABLE IF NOT EXISTS workout
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT REFERENCES user_info (id) ON DELETE CASCADE,
    name        VARCHAR(255) NOT NULL,
    description TEXT                     DEFAULT '',
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at  TIMESTAMP WITH TIME ZONE,
    is_archived BOOLEAN                  DEFAULT false,
    UNIQUE (user_id, name)
);
CREATE INDEX IF NOT EXISTS workout_user_id_idx
    ON workout (user_id);
CREATE INDEX IF NOT EXISTS workout_is_archived_idx
    ON workout (is_archived);
CREATE INDEX IF NOT EXISTS workout_created_at_idx
    ON workout (created_at);

-- The exercise configuration table
-- The exercise will be linked to a workout
-- For safety check, can get the workout of this exercise and check the user id is correct
CREATE TABLE IF NOT EXISTS exercise
(
    id         BIGSERIAL PRIMARY KEY,
    workout_id BIGINT REFERENCES workout (id) ON DELETE CASCADE,
    type       VARCHAR(255) NOT NULL,
    weight     REAL         NOT NULL CHECK (weight > 0),
    reps       INT          NOT NULL CHECK (reps > 0),
    sets       INT          NOT NULL CHECK (sets > 0),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE
);
CREATE INDEX IF NOT EXISTS exercise_workout_id_idx
    ON exercise (workout_id);
CREATE INDEX IF NOT EXISTS exercise_created_at_idx
    ON exercise (created_at);

-- The workout instance table
-- Should really never be updated after created
CREATE TABLE IF NOT EXISTS workout_instance
(
    id         BIGSERIAL PRIMARY KEY,
    workout_id BIGINT REFERENCES workout (id) ON DELETE CASCADE,
    user_id    BIGINT REFERENCES user_info (id) ON DELETE CASCADE,
    start_time TIMESTAMP WITH TIME ZONE NOT NULL,
    end_time   TIMESTAMP WITH TIME ZONE NOT NULL,
    feeling    VARCHAR(255),
    notes      TEXT DEFAULT ''
);
CREATE INDEX IF NOT EXISTS workout_instance_workout_id_idx
    ON workout_instance (workout_id);
CREATE INDEX IF NOT EXISTS workout_instance_user_id_idx
    ON workout_instance (user_id);
CREATE INDEX IF NOT EXISTS workout_instance_start_time_idx
    ON workout_instance (start_time);

-- The exercise instance table
CREATE TABLE IF NOT EXISTS exercise_instance
(
    id                  BIGSERIAL PRIMARY KEY,
    workout_instance_id BIGINT REFERENCES workout_instance (id) ON DELETE CASCADE,
    user_id             BIGINT REFERENCES user_info (id) ON DELETE CASCADE,
    -- Going to use this type system instead of a foreign key on exercise
    type                VARCHAR(255)             NOT NULL,
    weight              REAL                     NOT NULL CHECK (weight > 0),
    reps                INT                      NOT NULL CHECK (reps > 0),
    sets                INT                      NOT NULL CHECK (sets > 0),
    -- created_at should match start_time from the workout_instance
    created_at          TIMESTAMP WITH TIME ZONE NOT NULL
);
CREATE INDEX IF NOT EXISTS exercise_instance_workout_instance_id_idx
    ON exercise_instance (workout_instance_id);
CREATE INDEX IF NOT EXISTS exercise_instance_user_id_idx
    ON exercise_instance (user_id);
CREATE INDEX IF NOT EXISTS exercise_instance_created_at_idx
    ON exercise_instance (created_at);
