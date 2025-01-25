-- V2025011814__Create_articles_table.sql
CREATE TYPE language_enum AS ENUM ('none', 'en', 'es', 'fr', 'de', 'pt', 'it', 'ja', 'zh', 'ru');

CREATE TYPE level_enum AS ENUM ('none', 'a1', 'a2', 'b1', 'b2', 'c1', 'c2');

CREATE TABLE articles (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(250) NOT NULL,
    content TEXT NOT NULL,
    language language_enum NOT NULL,
    level level_enum NOT NULL,
    average_rating DECIMAL(2,1),
    number_of_ratings INT,
    view_count INT,
    read_count INT,
    bookmark_count INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users(id)
);
