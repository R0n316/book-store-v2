INSERT INTO user_book(id,book_id,user_id,is_in_favorites,is_in_cart)
VALUES
(1,2,1,true,false),
(2,3,1,false,true),
(3,4,1,true,true),
(4,1,2,false,false),
(5,4,2,true,false),
(6,2,2,true,true);


SELECT SETVAL('user_book_id_seq',(SELECT MAX(id) FROM user_book));