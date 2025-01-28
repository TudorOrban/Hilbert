-- V2025012722__Update_articles_table.sql
ALTER TABLE articles
DROP COLUMN content;

ALTER TABLE articles
ADD COLUMN content TEXT;