-- V202501301602__Create_bot_chats_table.sql
CREATE TABLE bot_chats (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    language language_enum,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_message_from_user BOOLEAN,
    last_message_content TEXT,
    last_message_date TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE INDEX idx_user_id ON bot_chats(user_id);
