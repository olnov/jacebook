DROP TABLE IF EXISTS comments;

CREATE TABLE comments (
    id bigserial PRIMARY KEY,
    content varchar(200) NOT NULL,
    post_id bigserial NOT NULL,
    user_id bigserial NOT NULL,
    FOREIGN KEY (post_id) REFERENCES posts(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);