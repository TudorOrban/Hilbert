-- V2025012824__Create_chat_messages_table.sql
CREATE TABLE chat_messages (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    chat_id INT NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (first_user_id) REFERENCES users(id),
    FOREIGN KEY (second_user_id) REFERENCES users(id),
    CONSTRAINT unique_chat UNIQUE (first_user_id, second_user_id),
);

CREATE INDEX idx_chat_id ON chat_messages(id);