insert into book_review (id,book_id, user_id, content, likes, dislikes)
values
    (1,3, 1, 'I thoroughly enjoyed this book. The plot was engaging, and the characters were well-developed. I found myself unable to put it down until I reached the very end.', 15, 2),
    (2,7, 2, 'This book was a bit slow to start, but it picked up about halfway through. The author''s writing style was unique and captivating, making the story more enjoyable as it progressed.', 8, 1),
    (3,2, 1, 'I was disappointed by this book. The storyline was predictable, and the characters lacked depth. I found it difficult to stay engaged and ultimately felt that it was a waste of my time.', 3, 10),
    (4,3, 2, 'This book was a fantastic read! The mystery kept me guessing until the very end, and the twists and turns were expertly executed. I highly recommend it to anyone who loves a good thriller.', 20, 0),
    (5,5, 1, 'The world-building in this book was incredible. I felt fully immersed in the story and connected with the characters on a deep level. I only wish the author had delved a bit more into some of the subplots.', 12, 3);

SELECT SETVAL('book_review_id_seq',(SELECT MAX(id) FROM book_review))