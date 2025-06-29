-- V202506261151__Create_grammar_lessons_table.sql
CREATE TABLE grammar_lessons (
    id SERIAL PRIMARY KEY,
    creator_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    exercises_data TEXT NOT NULL,
    FOREIGN KEY (creator_id) REFERENCES users(id)
);

CREATE INDEX idx_lessons_creator_id ON grammar_lessons(creator_id);