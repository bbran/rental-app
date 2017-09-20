DROP TABLE IF EXISTS apartments;

CREATE TABLE apartments (
	id BIGSERIAL PRIMARY KEY NOT NULL,
	number_of_bedrooms INTEGER NOT NULL,
	number_of_bathrooms INTEGER NOT NULL,
	square_footage INTEGER NOT NULL,
	rent INTEGER NOT NULL,
	address VARCHAR(255) NOT NULL,
	city VARCHAR(255) NOT NULL,
	state VARCHAR(255) NOT NULL,
	zip VARCHAR(255) NOT NULL
);