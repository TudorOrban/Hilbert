-- V2025013016__Alter_chats_table.sql
ALTER TABLE chats
DROP COLUMN is_second_user_chatbot;

DELETE FROM users WHERE id = 9999;