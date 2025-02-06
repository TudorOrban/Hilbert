-- V202502061730__Create_user_settings_table.sql
CREATE TABLE user_settings (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    settings TEXT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE INDEX idx_user_settings_user_id ON user_settings(user_id);
