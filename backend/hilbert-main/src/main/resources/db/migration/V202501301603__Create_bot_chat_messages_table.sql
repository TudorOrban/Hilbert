-- V202501301603__Create_bot_chat_messages_table.sql
CREATE TABLE bot_chat_messages (
    id SERIAL PRIMARY KEY,
    bot_chat_id INT NOT NULL,
    is_user BOOLEAN,
    content TEXT NOT NULL,
    translation TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (bot_chat_id) REFERENCES bot_chats(id)
);

CREATE INDEX idx_bot_chat_id ON bot_chat_messages(id);