-- V202501112153__Create_exercises_table.sql
CREATE TABLE exercises (
    id SERIAL PRIMARY KEY,
    creator_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    exercise_data TEXT NOT NULL,
    FOREIGN KEY (creator_id) REFERENCES users(id)
);

CREATE INDEX idx_exercises_creator_id ON exercises(creator_id);
