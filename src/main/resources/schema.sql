-- Drop table if it exists
DROP TABLE IF EXISTS books;

-- Create books table
CREATE TABLE books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isbn VARCHAR(20) NOT NULL,
    publish_year INT,
    genre VARCHAR(100),
    available BOOLEAN NOT NULL DEFAULT TRUE
);

-- Add some sample data
INSERT INTO books (title, author, isbn, publish_year, genre, available)
VALUES
    ('The Hobbit', 'J.R.R. Tolkien', '978-0547928227', 1937, 'Fantasy', true),
    ('1984', 'George Orwell', '978-0451524935', 1949, 'Dystopian', true),
    ('To Kill a Mockingbird', 'Harper Lee', '978-0060935467', 1960, 'Fiction', true),
    ('The Great Gatsby', 'F. Scott Fitzgerald', '978-0743273565', 1925, 'Fiction', true);