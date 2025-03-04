-----------------
-- START FLYWAY --
-----------------
CREATE TABLE IF NOT EXISTS flyway_schema_history (
	installed_rank int4 NOT NULL,
	"version" varchar(50) NULL,
	description varchar(200) NOT NULL,
	"type" varchar(20) NOT NULL,
	script varchar(1000) NOT NULL,
	checksum int4 NULL,
	installed_by varchar(100) NOT NULL,
	installed_on timestamp NOT NULL DEFAULT now(),
	execution_time int4 NOT NULL,
	success bool NOT NULL,
	CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank)
);

-----------------
-- CACHE TABLE --
-----------------
CREATE TABLE IF NOT EXISTS users (
    user_id SERIAL NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (user_id)
);


CREATE TABLE IF NOT EXISTS checklist (
    checklist_id SERIAL NOT NULL,
    name VARCHAR(255) NOT NULL,
    user_id INT NOT NULL,
    CONSTRAINT checklist_id_pkey PRIMARY KEY (checklist_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS checklist_item (
    checklist_item_id SERIAL NOT NULL,
    item_name VARCHAR(255) NOT NULL,
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    checklist_id INT NOT NULL,
    CONSTRAINT checklist_item_pkey PRIMARY KEY (checklist_item_id),
    CONSTRAINT fk_checklist FOREIGN KEY (checklist_id) REFERENCES checklist(checklist_id) ON DELETE CASCADE
);