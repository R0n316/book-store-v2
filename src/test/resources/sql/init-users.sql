INSERT INTO users(id,username,password,role)
VALUES
    (1,'alex','{noop}123','USER'),
    (2,'test','{noop}test','USER');

SELECT SETVAL('users_id_seq',(SELECT MAX(id) FROM users));