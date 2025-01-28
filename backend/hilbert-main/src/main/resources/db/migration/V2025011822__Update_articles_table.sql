-- V2025011822__Update_articles_table.sql

ALTER TABLE articles
ADD COLUMN description VARCHAR(255);

-- Add topics
CREATE TABLE topics (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE article_topics (
    article_id INT NOT NULL,
    topic_id INT NOT NULL,
    PRIMARY KEY(article_id, topic_id),
    FOREIGN KEY (article_id) REFERENCES articles(id),
    FOREIGN KEY (topic_id) REFERENCES topics(id)
);

CREATE TYPE article_status_enum AS ENUM ('public', 'private', 'draft');

ALTER TABLE articles
ADD COLUMN status article_status_enum DEFAULT 'private';

ALTER TABLE articles
ADD COLUMN word_count INT;

ALTER TABLE articles
ADD COLUMN comment_count INT;