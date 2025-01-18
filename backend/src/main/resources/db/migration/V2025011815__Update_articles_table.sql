-- V2025011815__Migrate_articles_table.sql

-- Drop existing language and level columns
ALTER TABLE articles DROP COLUMN language;
ALTER TABLE articles DROP COLUMN level;

-- Drop existing enum types
DROP TYPE language_enum;
DROP TYPE level_enum;

-- Create new enum types with Java values
CREATE TYPE language_enum AS ENUM ('NONE', 'ENGLISH', 'SPANISH', 'FRENCH', 'GERMAN', 'PORTUGUESE', 'ITALIAN', 'JAPANESE', 'CHINESE', 'RUSSIAN');
CREATE TYPE level_enum AS ENUM ('NONE', 'A1', 'A2', 'B1', 'B2', 'C1', 'C2');

-- Add language and level columns back with the new enum types
ALTER TABLE articles ADD COLUMN language language_enum NOT NULL;
ALTER TABLE articles ADD COLUMN level level_enum NOT NULL;