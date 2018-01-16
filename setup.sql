
CREATE TABLE users (
 user_id INT NOT NULL,
 email VARCHAR(100),
 password_hash CHAR(128),
 google_uid VARCHAR(255),
 google_token VARCHAR(255),
 google_expires_at DATETIME,
 google_refresh_token VARCHAR(255),
 name VARCHAR(30) NOT NULL,
 introduction TEXT NOT NULL,
 icon_image_file VARCHAR(100),
 created_at DATETIME NOT NULL,
 updated_at DATETIME NOT NULL,
 is_valid INT NOT NULL,
 is_owner INT NOT NULL
);

ALTER TABLE users ADD CONSTRAINT PK_users PRIMARY KEY (user_id);
ALTER TABLE users CHANGE user_id user_id INT NOT NULL AUTO_INCREMENT;


CREATE TABLE artists (
 artist_id INT NOT NULL,
 user_id INT NOT NULL,
 name VARCHAR(30) NOT NULL,
 introduction TEXT NOT NULL,
 header_image_file VARCHAR(100),
 created_at DATETIME NOT NULL,
 updated_at DATETIME NOT NULL
);
ALTER TABLE artists ADD CONSTRAINT PK_artists PRIMARY KEY (artist_id);
ALTER TABLE artists ADD CONSTRAINT FK_artists_0 FOREIGN KEY (user_id) REFERENCES users (user_id) ON UPDATE RESTRICT ON DELETE RESTRICT;



CREATE TABLE musics (
 music_id INT NOT NULL,
 artist_id INT NOT NULL,
 youtube_video_id VARCHAR(100) NOT NULL,
 view_count INT NOT NULL,
 title VARCHAR(50) NOT NULL,
 description TEXT NOT NULL,
 created_at DATETIME NOT NULL,
 updated_at DATETIME NOT NULL,
 is_deleted INT NOT NULL
);
ALTER TABLE musics ADD CONSTRAINT PK_musics PRIMARY KEY (music_id);
ALTER TABLE musics ADD CONSTRAINT FK_musics_0 FOREIGN KEY (artist_id) REFERENCES artists (artist_id) ON UPDATE RESTRICT ON DELETE RESTRICT;

CREATE TABLE votes (
 vote_id INT NOT NULL,
 user_id INT NOT NULL,
 artist_id INT NOT NULL,
 spent_tickets INT NOT NULL,
 created_at DATETIME NOT NULL
);
ALTER TABLE votes ADD CONSTRAINT PK_votes PRIMARY KEY (vote_id);
ALTER TABLE votes ADD CONSTRAINT FK_votes_0 FOREIGN KEY (user_id) REFERENCES users (user_id) ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE votes ADD CONSTRAINT FK_votes_1 FOREIGN KEY (artist_id) REFERENCES artists (artist_id) ON UPDATE RESTRICT ON DELETE RESTRICT;

CREATE TABLE tags (
 tag_id INT NOT NULL,
 music_id INT NOT NULL,
 name VARCHAR(30) NOT NULL,
 score_average FLOAT(10) NOT NULL,
 score_count INT NOT NULL
);
ALTER TABLE tags ADD CONSTRAINT PK_tags PRIMARY KEY (tag_id);
ALTER TABLE tags ADD CONSTRAINT FK_tags_0 FOREIGN KEY (music_id) REFERENCES musics (music_id) ON UPDATE RESTRICT ON DELETE RESTRICT;

CREATE TABLE tag_scores (
 user_id INT NOT NULL,
 tag_id INT NOT NULL,
 score INT NOT NULL
);
ALTER TABLE tag_scores ADD CONSTRAINT PK_tag_scores PRIMARY KEY (user_id,tag_id);
ALTER TABLE tag_scores ADD CONSTRAINT FK_tag_scores_0 FOREIGN KEY (user_id) REFERENCES users (user_id) ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE tag_scores ADD CONSTRAINT FK_tag_scores_1 FOREIGN KEY (tag_id) REFERENCES tags (tag_id) ON UPDATE RESTRICT ON DELETE RESTRICT;

CREATE TABLE points (
 user_id INT NOT NULL,
 point_count INT NOT NULL,
 vote_point_count INT NOT NULL
);
ALTER TABLE points ADD CONSTRAINT PK_points PRIMARY KEY (user_id);
ALTER TABLE points ADD CONSTRAINT FK_points_0 FOREIGN KEY (user_id) REFERENCES users (user_id) ON UPDATE RESTRICT ON DELETE RESTRICT;

CREATE TABLE point_get_histories (
 user_id INT NOT NULL,
 created_at DATETIME NOT NULL,
 got_points INT NOT NULL,
 description TEXT
);
ALTER TABLE point_get_histories ADD CONSTRAINT PK_point_get_histories PRIMARY KEY (user_id,created_at);
ALTER TABLE point_get_histories ADD CONSTRAINT FK_point_get_histories_0 FOREIGN KEY (user_id) REFERENCES points (user_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


CREATE TABLE comments (
 comment_id INT NOT NULL,
 user_id INT NOT NULL,
 content TEXT NOT NULL,
 music_id INT NOT NULL,
 score_plus_count INT NOT NULL,
 score_minus_count INT NOT NULL,
 created_at DATETIME NOT NULL,
 is_deleted INT NOT NULL
);
ALTER TABLE comments ADD CONSTRAINT PK_comments PRIMARY KEY (comment_id);
ALTER TABLE comments ADD CONSTRAINT FK_comments_0 FOREIGN KEY (user_id) REFERENCES users (user_id) ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE comments ADD CONSTRAINT FK_comments_1 FOREIGN KEY (music_id) REFERENCES musics (music_id) ON UPDATE RESTRICT ON DELETE RESTRICT;

CREATE TABLE comment_scores (
 user_id INT NOT NULL,
 comment_id INT NOT NULL,
 score INT NOT NULL
);
ALTER TABLE comment_scores ADD CONSTRAINT PK_comment_scores PRIMARY KEY (user_id,comment_id);
ALTER TABLE comment_scores ADD CONSTRAINT FK_comment_scores_0 FOREIGN KEY (user_id) REFERENCES users (user_id) ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE comment_scores ADD CONSTRAINT FK_comment_scores_1 FOREIGN KEY (comment_id) REFERENCES comments (comment_id) ON UPDATE RESTRICT ON DELETE RESTRICT;

CREATE TABLE advertisements (
 ad_id INT NOT NULL,
 user_Id INT NOT NULL,
 music_id INT NOT NULL,
 spent_points INT NOT NULL,
 created_at DATETIME NOT NULL
);
ALTER TABLE advertisements ADD CONSTRAINT PK_ads PRIMARY KEY (ad_id);
ALTER TABLE advertisements ADD CONSTRAINT FK_ads_0 FOREIGN KEY (user_Id) REFERENCES users (user_id) ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE advertisements ADD CONSTRAINT FK_ads_1 FOREIGN KEY (music_id) REFERENCES musics (music_id) ON UPDATE RESTRICT ON DELETE RESTRICT;

CREATE TABLE daily_view_histories (
 music_id INT NOT NULL,
 date DATETIME NOT NULL,
 views INT NOT NULL
);
ALTER TABLE daily_view_histories ADD CONSTRAINT PK_daily_view_histories PRIMARY KEY (music_id,date);
ALTER TABLE daily_view_histories ADD CONSTRAINT FK_daily_view_histories_0 FOREIGN KEY (music_id) REFERENCES musics (music_id) ON UPDATE RESTRICT ON DELETE RESTRICT;

CREATE TABLE vote_ticket_get_histories (
 user_id INT NOT NULL,
 created_at DATETIME NOT NULL,
 got_tickets INT NOT NULL,
 description TEXT
);
ALTER TABLE vote_ticket_get_histories ADD CONSTRAINT PK_vote_ticket_get_histories PRIMARY KEY (user_id,created_at);
ALTER TABLE vote_ticket_get_histories ADD CONSTRAINT FK_vote_ticket_get_histories_0 FOREIGN KEY (user_id) REFERENCES points (user_id) ON UPDATE RESTRICT ON DELETE RESTRICT;

CREATE TABLE messages (
 artist_id INT NOT NULL,
 message_id INT NOT NULL,
 user_id INT NOT NULL,
 content TEXT NOT NULL,
 response_to_id INT,
 created_at DATETIME NOT NULL,
 is_deleted INT NOT NULL
);
ALTER TABLE messages ADD CONSTRAINT PK_messages PRIMARY KEY (artist_id,message_id);
ALTER TABLE messages ADD CONSTRAINT FK_messages_0 FOREIGN KEY (artist_id) REFERENCES artists (artist_id) ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE messages ADD CONSTRAINT FK_messages_1 FOREIGN KEY (user_id) REFERENCES users (user_id) ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE messages ADD CONSTRAINT FK_messages_2 FOREIGN KEY (artist_id,response_to_id) REFERENCES messages (artist_id,message_id) ON UPDATE RESTRICT ON DELETE RESTRICT;

CREATE TABLE search_histories (
 user_id INT NOT NULL,
 id INT NOT NULL,
 created_at DATETIME NOT NULL,
 keyword VARCHAR(30) NOT NULL
);
ALTER TABLE search_histories ADD CONSTRAINT PK_search_histories PRIMARY KEY (user_id,id);
ALTER TABLE search_histories ADD CONSTRAINT FK_search_histories_0 FOREIGN KEY (user_id) REFERENCES users (user_id) ON UPDATE RESTRICT ON DELETE RESTRICT;

CREATE TABLE mylists (
 mylist_id INT NOT NULL,
 user_id INT NOT NULL,
 created_at DATETIME NOT NULL,
 updated_at DATETIME NOT NULL
);
ALTER TABLE mylists ADD CONSTRAINT PK_mylists PRIMARY KEY (mylist_id);
ALTER TABLE mylists ADD CONSTRAINT FK_mylists_0 FOREIGN KEY (user_id) REFERENCES users (user_id) ON UPDATE RESTRICT ON DELETE RESTRICT;

CREATE TABLE mylist_details (
 mylist_id INT NOT NULL,
 mylist_detail_id INT NOT NULL,
 music_id INT,
 artist_id INT,
 created_at DATETIME NOT NULL,
 updated_at DATETIME NOT NULL
);
ALTER TABLE mylist_details ADD CONSTRAINT PK_mylist_details PRIMARY KEY (mylist_id,mylist_detail_id);
ALTER TABLE mylist_details ADD CONSTRAINT FK_mylist_details_0 FOREIGN KEY (mylist_id) REFERENCES mylists (mylist_id) ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE mylist_details ADD CONSTRAINT FK_mylist_details_1 FOREIGN KEY (music_id) REFERENCES musics (music_id) ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE mylist_details ADD CONSTRAINT FK_mylist_details_2 FOREIGN KEY (artist_id) REFERENCES artists (artist_id) ON UPDATE RESTRICT ON DELETE RESTRICT;



