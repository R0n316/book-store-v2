INSERT INTO users(id, username, password, role)
VALUES
(1, 'user1', '{noop}1234', 'USER'),
(2, 'user2', '{noop}1234', 'USER');

INSERT INTO category(id, name)
VALUES
    (1, 'Фэнтези'),
    (2, 'Детективы'),
    (3, 'Боевики'),
    (4, 'Проза'),
    (5, 'Бизнес'),
    (6, 'Эзотерика'),
    (7, 'Психология'),
    (8, 'Мода'),
    (9, 'Кулинария'),
    (10, 'Биография'),
    (11, 'Хобби');

SELECT SETVAL('category_id_seq',(SELECT MAX(id) FROM category));

INSERT INTO book (id, author, rating, price, publisher, series, year_of_publishing, isbn, number_of_pages, size, cover_type, circulation, weight, age_restrictions, name, category_id, description, language,image_path)
VALUES
    (1, 'Leo Tolstoy', 4.5, 15, 'Oxford University Press', NULL, 1869, '9780199536117', 1296, '6.1 x 1.9 x 9.2 inches', 'Paperback', 100000, 1588, 0, 'War and Peace', 1, 'A monumental novel that explores the impact of the Napoleonic Wars on Tsarist Russia.', 'EN','71wXZB-VtBL._AC_UF1000,1000_QL80_.jpg'),
    (2, 'Fyodor Dostoevsky', 4.3, 12, 'Vintage Classics', NULL, 1866, '9780099503271', 672, '5.1 x 1.8 x 7.8 inches', 'Paperback', 100000, 672, 0, 'Crime and Punishment', 1, 'A psychological thriller that delves into the mind of a murderer.', 'EN','Crimeandpunishmentcover.png'),
    (3, 'Jane Austen', 4.2, 10, 'Penguin Classics', NULL, 1813, '9780141439562', 480, '5.1 x 1.3 x 7.8 inches', 'Paperback', 100000, 480, 0, 'Pride and Prejudice', 1, 'A romantic novel that explores the themes of love, marriage, and social class.', 'EN','MV5BMTA1NDQ3NTcyOTNeQTJeQWpwZ15BbWU3MDA0MzA4MzE@._V1_.jpg'),
    (4, 'Charles Dickens', 4.1, 14, 'Penguin Classics', NULL, 1859, '9780141439593', 944, '5.1 x 2 x 7.8 inches', 'Paperback', 100000, 944, 0, 'A Tale of Two Cities', 1, 'A historical novel set in the French Revolution that explores the themes of duality and resurrection.', 'EN','images.jpg'),
    (5, 'Mark Twain', 4.0, 11, 'Oxford University Press', NULL, 1884, '9780199535543', 416, '5.1 x 0.9 x 7.8 inches', 'Paperback', 100000, 416, 0, 'Adventures of Huckleberry Finn', 1, 'A satirical novel that explores the themes of racism and morality.', 'EN','91sBZnKzEfL._AC_UF1000,1000_QL80_.jpg'),
    (6, 'Herman Melville', 3.9, 13, 'Penguin Classics', NULL, 1851, '9780141439814', 672, '5.1 x 1.7 x 7.8 inches', 'Paperback', 100000, 672, 0, 'Moby-Dick', 1, 'An epic novel that explores the themes of obsession and the nature of good and evil.', 'EN','81R91ODA9DL._AC_UF1000,1000_QL80_.jpg'),
    (7, 'Gustave Flaubert', 3.8, 16, 'Penguin Classics', NULL, 1857, '9780140449281', 512, '5.1 x 1.3 x 7.8 inches', 'Paperback', 100000, 512, 0, 'Madame Bovary', 1, 'A realist novel that explores the themes of adultery and the nature of human desire.', 'EN','41Of4Xao87L._AC_UF1000,1000_QL80_.jpg'),
    (8, 'George Eliot', 3.7, 12, 'Penguin Classics', NULL, 1861, '9780140434234', 864, '5.1 x 2 x 7.8 inches', 'Paperback', 100000, 864, 0, 'Silas Marner', 1, 'A pastoral novel that explores the themes of redemption and the nature of human relationships.', 'EN','71ZvgiUWuJL._AC_UF1000,1000_QL80_.jpg'),
    (9, 'Thomas Hardy', 3.6, 10, 'Penguin Classics', NULL, 1874, '9780140434241', 448, '5.1 x 0.9 x 7.8 inches', 'Paperback', 100000, 448, 0, 'Far from the Madding Crowd', 1, 'A pastoral novel that explores the themes of love and the nature of human relationships.', 'EN','MV5BNzI4NzUwNDgwN15BMl5BanBnXkFtZTgwNTI3MjMwNTE@._V1_.jpg'),
    (10, 'Emily Bronte', 3.5, 13, 'Penguin Classics', NULL, 1847, '9780141439548', 448, '5.1 x 0.9 x 7.8 inches', 'Paperback', 100000, 448, 0, 'Wuthering Heights', 1, 'A gothic novel that explores the themes of love and the nature of human relationships.', 'EN','81dvA4tU0rL._AC_UF1000,1000_QL80_.jpg');

SELECT SETVAL('book_id_seq',(SELECT MAX(id) FROM book));

INSERT INTO book_review (id,book_id, user_id, content,created_at)
VALUES
    (1,1, 1, 'A masterpiece that beautifully depicts the human condition amidst war. A must-read!',	to_timestamp(1721530800)),
    (2,2, 2, 'A gripping tale of crime and redemption. Dostoevsky''s psychological insight is unparalleled.',to_timestamp(1721531100)),
    (3,3, 1, 'Austen''s wit and social commentary make this a delightful read. The romance is a bonus!',to_timestamp(1721531400)),
    (4,4, 2, 'Dickens''s vivid portrayal of the French Revolution is captivating. The duality theme is intriguing.',to_timestamp(1721531700)),
    (5,5,1, 'Twain''s satire on racism and morality is thought-provoking. Huck''s journey is memorable.',to_timestamp(1721531700)),
    (6,6, 2, 'Melville''s epic tale of obsession is a deep dive into the human psyche. A whale of a read!',to_timestamp(1721532300)),
    (7,7, 1, 'Flaubert''s realist narrative of adultery and desire is a stark reflection of societal norms. Provocative!',	to_timestamp(1721532600)),
    (8,8, 2, 'Eliot''s pastoral tale of redemption is heartwarming. Silas Marner''s transformation is inspiring.',	to_timestamp(1721532900)),
    (9,9, 1, 'Hardy''s portrayal of love and relationships is compelling. The rural setting adds depth.',to_timestamp(1721533200)),
    (10,10, 2, 'Bronte''s gothic romance is intense and passionate. Heathcliff and Catherine''s love story is unforgettable.',to_timestamp(1721533500));

SELECT SETVAL('book_review_id_seq',(SELECT MAX(id) FROM book_review));


INSERT INTO review_reaction (id,user_id, book_review_id, reaction)
VALUES
    (1,1, 1, 'LIKE'),
    (2,2, 2, 'DISLIKE'),
    (3,1, 3, 'LIKE'),
    (4,2, 4, 'LIKE'),
    (5,1, 5, 'LIKE'),
    (6,2, 6, 'DISLIKE'),
    (7,1, 7, 'LIKE'),
    (8,2, 8, 'LIKE'),
    (9,1, 9, 'LIKE'),
    (10,2, 10, 'LIKE');

SELECT SETVAL('review_reaction_id_seq',(SELECT MAX(id) FROM review_reaction));