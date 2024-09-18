CREATE DATABASE map_collab;
\c map_collab;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "postgis";

-- public.users definition

-- Drop table

-- DROP TABLE public.users;

CREATE TABLE public.users (
	id varchar(255) DEFAULT uuid_generate_v4() NOT NULL,
	username varchar(255) NOT NULL,
	"password" varchar(255) NOT NULL,
	company varchar NULL,
	CONSTRAINT user_pk PRIMARY KEY (id)
);
-- public.maps definition

-- Drop table

-- DROP TABLE public.maps;

CREATE TABLE public.maps (
	id varchar DEFAULT uuid_generate_v4() NOT NULL,
	map_name varchar DEFAULT 'map'::character varying NOT NULL,
	user_id varchar NOT NULL,
	CONSTRAINT maps_pk PRIMARY KEY (id)
);


-- public.maps foreign keys

ALTER TABLE public.maps ADD CONSTRAINT maps_user_fk FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;
-- public.map_drawing definition

-- Drop table

-- DROP TABLE public.map_drawing;

CREATE TABLE public.map_drawing (
	id varchar NOT NULL,
	shape public.geometry NOT NULL,
	map_id varchar NOT NULL,
	CONSTRAINT newtable_pk PRIMARY KEY (id)
);
CREATE INDEX newtable_column1_idx ON public.map_drawing USING gist (shape);


-- public.map_drawing foreign keys

ALTER TABLE public.map_drawing ADD CONSTRAINT mapdrawing_maps_fk FOREIGN KEY (map_id) REFERENCES public.maps(id);