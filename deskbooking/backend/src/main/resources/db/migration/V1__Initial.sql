CREATE TABLE app_user
(
    id SERIAL,
    email character varying(255),
    password character varying(255)
);

ALTER TABLE app_user
    ADD CONSTRAINT app_user_pkey PRIMARY KEY (id);

CREATE TABLE office
(
    id SERIAL,
    is_available boolean ,
    "number" integer NOT NULL
);

ALTER TABLE office
    ADD CONSTRAINT office_pkey PRIMARY KEY (id);

CREATE TABLE booking
(
    id SERIAL,
    date character varying(255),
    office_id integer NOT NULL,
    user_id integer NOT NULL
);

ALTER TABLE booking
    ADD CONSTRAINT booking_pkey PRIMARY KEY (id);
ALTER TABLE booking
    ADD CONSTRAINT booking_app_user_fk FOREIGN KEY (user_id) REFERENCES app_user (id);
ALTER TABLE booking
    ADD CONSTRAINT booking_office_fk FOREIGN KEY (office_id) REFERENCES office (id);

INSERT INTO office
VALUES
    (1,true,201),
    (2,true,202),
    (3,true,203),
    (4,true,204),
    (5,true,205),
    (6,true,206),
    (7,true,207),
    (8,true,208),
    (9,true,209),
    (10,true,210),
    (11,true,211),
    (12,true,212),
    (13,true,213),
    (14,true,214),
    (15,true,215);