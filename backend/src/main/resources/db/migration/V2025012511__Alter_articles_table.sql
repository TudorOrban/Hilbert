-- V2025012511__Alter_articles_table.sql
ALTER TABLE articles
ALTER COLUMN translated_content type JSONB;