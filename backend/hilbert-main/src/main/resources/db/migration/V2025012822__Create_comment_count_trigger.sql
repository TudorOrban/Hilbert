-- V2025012822__Create_comment_count_trigger.sql

-- Create increment count function
CREATE OR REPLACE FUNCTION increment_comment_count()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE articles
    SET comment_count = comment_count + 1
    WHERE id = NEW.article_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create trigger
CREATE TRIGGER trg_increment_comment_count
AFTER INSERT ON article_comments
FOR EACH ROW
EXECUTE FUNCTION increment_comment_count();