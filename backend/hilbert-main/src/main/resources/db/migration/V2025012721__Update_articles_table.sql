-- V2025012721__Update_articles_table.sql
ALTER TABLE articles
ALTER COLUMN content TYPE TEXT USING content::TEXT;
