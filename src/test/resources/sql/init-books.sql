INSERT INTO category
VALUES
(1,'Fantasy'),
(2,'Detectives'),
(3,'Militants'),
(4,'Prose'),
(5,'Business');

SELECT SETVAL('category_id_seq',(SELECT MAX(id) FROM category));

INSERT INTO book (id,author, rating, price, image_path, publisher, series, year_of_publishing, isbn, number_of_pages, size, cover_type, circulation, weight, age_restrictions, name, language,category_id)
VALUES
    (1,'George Orwell', 4.17, 999, 'https://placeholder.com/150x200?text=1984', 'Secker & Warburg', null, 1949, '9780451524935', 328, 'Paperback', 'Softcover', 30000000, 198, 0, '1984', 'en',1),
    (2,'J.K. Rowling', 4.44, 1200, 'https://placeholder.com/150x200?text=Harry+Potter+and+the+Philosopher''s+Stone', 'Bloomsbury Publishing', 'Harry Potter', 1997, '9780545790352', 309, 'Paperback', 'Softcover', 500000000, 368, 9, 'Harry Potter and the Philosopher''s Stone', 'en',2),
    (3,'Ernest Hemingway', 3.88, 799, 'https://placeholder.com/150x200?text=A+Farewell+to+Arms', 'Charles Scribner''s Sons', null, 1929, '9780684803393', 251, 'Paperback', 'Softcover', 5000000, 216, 0, 'A Farewell to Arms', 'en',3),
    (4,'F. Scott Fitzgerald', 3.82, 899, 'https://placeholder.com/150x200?text=The+Great+Gatsby', 'Charles Scribner''s Sons', null, 1925, '9780743273565', 180, 'Paperback', 'Softcover', 25000000, 134, 0, 'The Great Gatsby', 'en',3),
    (5,'Jane Austen', 4.03, 699, 'https://placeholder.com/150x200?text=Pride+and+Prejudice', 'Thomas Egerton', null, 1813, '9780140432108', 432, 'Paperback', 'Softcover', 20000000, 272, 0, 'Pride and Prejudice', 'en',4),
    (6,'Charles Dickens', 4.02, 1199, 'https://placeholder.com/150x200?text=A+Tale+of+Two+Cities', 'Bradbury and Evans', null, 1859, '9780141439542', 864, 'Paperback', 'Softcover', 20000000, 464, 0, 'A Tale of Two Cities', 'en',4),
    (7,'Mark Twain', 3.97, 799, 'https://placeholder.com/150x200?text=Adventures+of+Huckleberry+Finn', 'American Publishing Company', null, 1884, '9780486268700', 368, 'Paperback', 'Softcover', 20000000, 224, 0, 'Adventures of Huckleberry Finn', 'en',5),
    (8,'Leo Tolstoy', 4.17, 1499, 'https://placeholder.com/150x200?text=War+and+Peace', 'The Russian Messenger', null, 1869, '9780140449144', 1296, 'Paperback', 'Softcover', 20000000, 864, 0, 'War and Peace', 'en',1),
    (9,'Gabriel Garcia Marquez', 4.12, 999, 'https://placeholder.com/150x200?text=One+Hundred+Years+of+Solitude', 'Editorial Sudamericana', null, 1967, '9780060882516', 417, 'Paperback', 'Softcover', 50000000, 208, 0, 'One Hundred Years of Solitude', 'es',2),
    (10,'Miguel de Cervantes Saavedra', 3.98, 1299, 'https://placeholder.com/150x200?text=Don+Quixote', 'Francisco de Robles', null, 1605, '9780140449090', 1056, 'Paperback', 'Softcover', 50000000, 480, 0, 'Don Quixote', 'es',5);

SELECT SETVAL('book_id_seq',(SELECT MAX(id) FROM book));