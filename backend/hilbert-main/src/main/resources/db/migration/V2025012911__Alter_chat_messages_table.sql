-- V2025012911__Alter_chat_messages_table.sql
ALTER TABLE chat_messages
DROP CONSTRAINT chat_messages_chat_id_fkey,
ADD CONSTRAINT chat_messages_chat_id_fkey FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE;