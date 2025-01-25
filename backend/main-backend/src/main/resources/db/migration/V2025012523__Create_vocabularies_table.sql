-- V2025012523__Create_vocabularies_table.sql

CREATE TABLE vocabularies (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    language language_enum,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON_CREATE CURRENT_TIMESTAMP,
    read_words JSONB,

    FOREIGN KEY (user_id) REFERENCES users(id)
);