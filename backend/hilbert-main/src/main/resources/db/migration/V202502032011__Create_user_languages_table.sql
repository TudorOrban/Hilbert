-- V202502032011__Create_user_languages_table.sql
CREATE TABLE user_languages (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    language language_enum NOT NULL,
    level level_enum,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE INDEX idx_user_languages_user_id ON user_languages(user_id);
CREATE INDEX idx_user_languages_language ON user_languages(language);