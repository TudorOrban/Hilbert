-- V2025012823__Create_chat_table.sql
CREATE TABLE chats (
    id SERIAL PRIMARY KEY,
    first_user_id INT NOT NULL,
    second_user_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_message_content TEXT,
    FOREIGN KEY (first_user_id) REFERENCES users(id),
    FOREIGN KEY (second_user_id) REFERENCES users(id),
    CONSTRAINT unique_chat UNIQUE (first_user_id, second_user_id),
);

CREATE INDEX idx_first_user_id ON chats(first_user_id);
CREATE INDEX idx_second_user_id ON chats(second_user_id);