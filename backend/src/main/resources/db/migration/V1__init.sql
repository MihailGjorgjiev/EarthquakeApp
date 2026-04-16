CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE earthquakes
(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    earthquake_id VARCHAR(10),
    magnitude NUMERIC NOT NULL ,
    mag_type VARCHAR(50) NOT NULL ,
    title VARCHAR(255) NOT NULL ,
    place VARCHAR(255) NOT NULL ,
    timestamp BIGINT NOT NULL,
    longitude NUMERIC NOT NULL ,
    latitude NUMERIC NOT NULL ,
    depth NUMERIC NOT NULL
);