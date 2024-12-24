-- Store user information
CREATE TABLE IF NOT EXISTS user_info
(
    id             BIGSERIAL PRIMARY KEY,
    email          VARCHAR(255) NOT NULL UNIQUE,
    first_name     VARCHAR(255) NOT NULL,
    last_name      VARCHAR(255) NOT NULL,
    is_verified    BOOLEAN                  DEFAULT false,
    created_at     TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at     TIMESTAMP WITH TIME ZONE,
    workout_streak INT                      DEFAULT 0,
    uses_metric    BOOLEAN                  DEFAULT false
);
CREATE INDEX IF NOT EXISTS user_info_email_idx
    ON user_info (email);

-- Table to store user authentication information
-- email and password will be used
CREATE TABLE IF NOT EXISTS user_auth
(
    email    VARCHAR(255) NOT NULL PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    enabled  BOOLEAN DEFAULT true,
    user_id  BIGINT REFERENCES user_info (id) ON DELETE CASCADE
);

-- Table to store user role information
CREATE TABLE IF NOT EXISTS user_role
(
    email VARCHAR(255) NOT NULL,
    role  VARCHAR(255) NOT NULL,
    -- If a user_auth row is deleted, this user_role will also be deleted
    CONSTRAINT fk_user_role_to_user_auth FOREIGN KEY (email) REFERENCES user_auth (email) ON DELETE CASCADE
);
CREATE UNIQUE INDEX idx_user_role_unique_email_and_role ON user_role (email, role);