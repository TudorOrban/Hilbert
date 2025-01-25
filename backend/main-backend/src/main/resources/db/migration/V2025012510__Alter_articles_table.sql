-- V2025012510__Alter_articles_table.sql
ALTER TABLE articles
ALTER COLUMN content type TEXT;

ALTER TABLE articles
ADD COLUMN translated_content JSON;