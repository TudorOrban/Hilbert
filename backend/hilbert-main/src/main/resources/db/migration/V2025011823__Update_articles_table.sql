-- V2025011823__Update_articles_table.sql

ALTER TABLE articles DROP COLUMN status;

DROP TYPE article_status_enum;

CREATE TYPE article_status_enum AS ENUM ('PUBLIC', 'PRIVATE', 'DRAFT');

ALTER TABLE articles ADD COLUMN status article_status_enum DEFAULT 'PRIVATE';
