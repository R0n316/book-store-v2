INSERT INTO book_review (id,book_id, user_id, content,created_at)
VALUES
    (1,5, 7, 'A masterpiece that beautifully depicts the human condition amidst war. A must-read!',	1721530800),
    (2,6, 8, 'A gripping tale of crime and redemption. Dostoevsky''s psychological insight is unparalleled.',1721531100),
    (3,7, 7, 'Austen''s wit and social commentary make this a delightful read. The romance is a bonus!',1721531400),
    (4,8, 8, 'Dickens''s vivid portrayal of the French Revolution is captivating. The duality theme is intriguing.',1721531700),
    (5,9,7, 'Twain''s satire on racism and morality is thought-provoking. Huck''s journey is memorable.',1721531700),
    (6,10, 8, 'Melville''s epic tale of obsession is a deep dive into the human psyche. A whale of a read!',1721532300),
    (7,11, 7, 'Flaubert''s realist narrative of adultery and desire is a stark reflection of societal norms. Provocative!',	1721532600),
    (8,12, 8, 'Eliot''s pastoral tale of redemption is heartwarming. Silas Marner''s transformation is inspiring.',	1721532900),
    (9,13, 7, 'Hardy''s portrayal of love and relationships is compelling. The rural setting adds depth.',1721533200),
    (10,14, 8, 'Bronte''s gothic romance is intense and passionate. Heathcliff and Catherine''s love story is unforgettable.',1721533500);

SELECT SETVAL('book_review_id_seq',(SELECT MAX(id) FROM book_review));


INSERT INTO review_reaction (id,user_id, book_review_id, reaction)
VALUES
    (1,7, 1, 'LIKE'),
    (2,8, 2, 'DISLIKE'),
    (3,7, 3, 'LIKE'),
    (4,8, 4, 'LIKE'),
    (5,7, 5, 'LIKE'),
    (6,8, 6, 'DISLIKE'),
    (7,7, 7, 'LIKE'),
    (8,8, 8, 'LIKE'),
    (9,7, 9, 'LIKE'),
    (10,8, 10, 'LIKE');

SELECT SETVAL('review_reaction_id_seq',(SELECT MAX(id) FROM review_reaction));