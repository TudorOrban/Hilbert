-- V2025013015__Alter_chats_table.sql
ALTER TABLE chats
ADD COLUMN is_second_user_chatbot BOOLEAN DEFAULT FALSE;

INSERT INTO users (id, username, email, password_hash)
VALUES (9999, 'HilbertChatbot', 'hilbertchatbot@emai.com', 'none');