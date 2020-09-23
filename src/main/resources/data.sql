INSERT INTO roles(name) values ('ROLE_USER');
INSERT INTO roles(name) values ('ROLE_ADMIN');

INSERT INTO users(email, password, username)
    values ('admin@gmail.com', '$2y$12$W9J3f0WGNWJ9uAPcgUtocOCqWcFprMIF0dunTgmtwPrl6Pph9Tj6m','admin');

INSERT INTO user_roles(user_id, role_id) values (1,2);