
CREATE TABLE test_inside.user
(
    id bigserial NOT NULL,
    name varchar(32) NOT NULL,
    password varchar(64) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT name UNIQUE (name)
);

CREATE TABLE test_inside.role
(
    id bigserial NOT NULL,
    name varchar(32) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE test_inside.users_roles
(
    user_id bigserial NOT NULL,
    role_id bigserial NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT user_id FOREIGN KEY (user_id)
        REFERENCES test_inside."user" (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID,
    CONSTRAINT role_id FOREIGN KEY (role_id)
        REFERENCES test_inside.role (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID
);

CREATE TABLE test_inside.message
    (
    id bigserial NOT NULL,
    message character varying NOT NULL,
    user_id bigserial NOT NULL,
    created_at timestamp default current_timestamp,
    PRIMARY KEY (id),
    CONSTRAINT user_id FOREIGN KEY (user_id)
        REFERENCES "test_inside"."user" (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID
    );



