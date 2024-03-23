
CREATE SCHEMA IF NOT EXISTS pawfect_match AUTHORIZATION admin

CREATE SEQUENCE pawfect_match.sq_pet_id START 1;
CREATE SEQUENCE pawfect_match.sq_owner_id START 1;
CREATE SEQUENCE pawfect_match.sq_match_id START 1;
CREATE SEQUENCE pawfect_match.sq_award_id START 1;
CREATE SEQUENCE pawfect_match.sq_address_id START 1;
CREATE SEQUENCE pawfect_match.sq_image_id START 1;

CREATE TABLE IF NOT EXISTS pawfect_match.PET_OWNER (
                                                       id BIGINT  PRIMARY KEY DEFAULT NEXTVAL('pawfect_match.sq_owner_id'),
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(25)
    );


CREATE TABLE IF NOT EXISTS pawfect_match.PET (
                                                 id BIGINT  PRIMARY KEY DEFAULT NEXTVAL('pawfect_match.sq_pet_id'),
    name VARCHAR(50) NOT NULL,
    age INT NOT NULL,
    type VARCHAR(25) NOT NULL,
    color VARCHAR(25),
    gender VARCHAR(10) NOT NULL,
    breed VARCHAR(25) NOT NULL,
    description VARCHAR(250),
    owner_id BIGINT REFERENCES pawfect_match.PET_OWNER(id)
    );

CREATE TABLE IF NOT EXISTS pawfect_match.PET_IMAGE (
                                                       id BIGINT  PRIMARY KEY DEFAULT NEXTVAL('pawfect_match.sq_image_id'),
    photo BYTEA,
    pet_id BIGINT REFERENCES pawfect_match.PET(id)
    );

CREATE TABLE IF NOT EXISTS pawfect_match.MATCH (
                                                   id BIGINT  PRIMARY KEY DEFAULT NEXTVAL('pawfect_match.sq_match_id'),
    pet_id_init_match BIGINT REFERENCES pawfect_match.PET(id),
    pet_id_response_match BIGINT REFERENCES pawfect_match.PET(id),
    full_match BOOLEAN DEFAULT FALSE,
    match_date TIMESTAMP
    );

CREATE TABLE IF NOT exists pawfect_match.AWARD (
                                                   id BIGINT  PRIMARY KEY DEFAULT NEXTVAL('pawfect_match.sq_award_id'),
    name VARCHAR(25) NOT NULL,
    ranking INT NOT NULL,
    pet_id BIGINT REFERENCES pawfect_match.PET(id)
    )

CREATE TABLE IF NOT exists pawfect_match.ADDRESS (
                                                     id BIGINT  PRIMARY KEY DEFAULT NEXTVAL('pawfect_match.sq_address_id'),
    country VARCHAR(25) NOT NULL,
    county VARCHAR(25) NOT NULL,
    city VARCHAR(25) NOT NULL,
    address VARCHAR(100) NOT NULL,
    postal_code INT NOT NULL,
    owner_id BIGINT REFERENCES pawfect_match.PET_OWNER(id)
    )
