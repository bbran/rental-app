DROP TABLE IF EXISTS apartments;

CREATE TABLE apartments (
	id BIGSERIAL PRIMARY KEY NOT NULL,
	number_of_bedrooms INTEGER NOT NULL,
	number_of_bathrooms INTEGER NOT NULL,
	square_footage INTEGER NOT NULL,
	rent INTEGER NOT NULL,
	address VARCHAR(255),
	city VARCHAR(255),
	state VARCHAR(20),
	zip_code VARCHAR(10),
	user_id BIGINT NOT NULL,
	is_active boolean NOT NULL DEFAULT FALSE
);