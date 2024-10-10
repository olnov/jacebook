CREATE TABLE likes (id BIGSERIAL PRIMARY KEY, user_id BIGINT NOT NULL, post_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
    UNIQUE(user_id, post_id)
 );