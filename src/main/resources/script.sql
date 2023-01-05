--CREATE USER docker;

--CREATE DATABASE sample_db;

---GRANT ALL PRIVILEGES ON DATABASE sample_db TO docker;

-- Drop table

-- DROP TABLE public.mt_param_groups;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp"; -- enable UUID

CREATE EXTENSION IF NOT EXISTS pgcrypto; -- enable to use crypto while saving data

CREATE TABLE public.mt_users (
	user_id uuid NOT NULL DEFAULT uuid_generate_v4(),
	user_name varchar NOT NULL,
	"password" varchar NOT NULL,
	created_date int8 NOT NULL DEFAULT (date_part('epoch'::text, now()) * 1000::double precision),
	created_by varchar NOT NULL,
	updated_date int8 NULL,
	updated_by varchar NULL,
	"version" int4 NULL DEFAULT 1,
	is_active bool NOT NULL DEFAULT true,
	CONSTRAINT mt_users_pk PRIMARY KEY (user_id),
	CONSTRAINT mt_users_un UNIQUE (user_name)
);


-- Create Trigger on mt_users to use pgcrypto in every INSERT

CREATE OR REPLACE FUNCTION trigger_function_password_hash()
    RETURNS trigger
    LANGUAGE 'plpgsql'
AS $BODY$

    BEGIN
        -- Check if password is null
        IF NEW.password IS NULL THEN
            RAISE EXCEPTION 'password cannot be null';
        END IF;
        
  NEW.password := crypt(NEW.password, gen_salt('bf', 8));

        RETURN NEW;
    END;
$BODY$;

CREATE OR REPLACE TRIGGER "generatePasswordUsingHash"
    BEFORE INSERT ON mt_users
    FOR EACH ROW
    EXECUTE FUNCTION trigger_function_password_hash();
	
	
-- Function to verify

CREATE OR REPLACE FUNCTION isUserAndPasswordValid(
    "uname" text,
    "plainPassw" text
)
    RETURNS boolean
    LANGUAGE 'plpgsql'
AS $BODY$
DECLARE
    hashedPassword VARCHAR(72);
    newHash  VARCHAR(60); 
    isValid  boolean := false;

    BEGIN

        hashedPassword := (SELECT password from mt_users WHERE user_name = "uname");
        
        newHash := crypt("plainPassw", hashedPassword);
        
  IF (newHash = hashedPassword) THEN 
            isValid := true;
        END IF;    

        RETURN isValid;
    END;
$BODY$;


CREATE TABLE public.mt_param_groups (
	param_group_id uuid NOT NULL DEFAULT uuid_generate_v4(),
	param_group_code varchar NOT NULL,
	param_group_name varchar NOT NULL,
	param_group_desc varchar NULL,
	parent_id uuid NULL,
	created_date int8 NOT NULL DEFAULT (date_part('epoch'::text, now()) * 1000::double precision),
	created_by uuid NOT NULL,
	updated_date int8 NULL,
	updated_by uuid NULL,
	"version" int4 NULL DEFAULT 1,
	is_active bool NOT NULL DEFAULT true,
	CONSTRAINT mt_param_groups_pk PRIMARY KEY (param_group_id),
	CONSTRAINT mt_param_groups_fk FOREIGN KEY (created_by) REFERENCES public.mt_users(user_id),
	CONSTRAINT mt_param_groups_fk_1 FOREIGN KEY (updated_by) REFERENCES public.mt_users(user_id)
);


-- Drop table

-- DROP TABLE public.mt_param_details;

CREATE TABLE public.mt_param_details (
	param_detail_id uuid NOT NULL DEFAULT uuid_generate_v4(),
	param_detail_code varchar NOT NULL,
	param_detail_name varchar NOT NULL,
	param_detail_desc varchar NULL,
	parent_id uuid NULL,
	created_date int8 NOT NULL DEFAULT (date_part('epoch'::text, now()) * 1000::double precision),
	created_by uuid NOT NULL,
	updated_date int8 NULL,
	updated_by uuid NULL,
	"version" int4 NULL DEFAULT 1,
	is_active bool NOT NULL DEFAULT true,
	param_group_id uuid NOT NULL,
	CONSTRAINT mt_param_details_pk PRIMARY KEY (param_detail_id),
	CONSTRAINT mt_param_details_fk FOREIGN KEY (param_group_id) REFERENCES public.mt_param_groups(param_group_id),
	CONSTRAINT mt_param_details_fk_1 FOREIGN KEY (created_by) REFERENCES public.mt_users(user_id),
	CONSTRAINT mt_param_details_fk_2 FOREIGN KEY (updated_by) REFERENCES public.mt_users(user_id)
);


-- SAMPLE DATA

insert into mt_users (user_name, "password", created_by) values ('admin', 'admin', 'superuser');
insert into mt_users (user_name, "password", created_by) values ('user1', 'user1', 'admin');
insert into mt_users (user_name, "password", created_by) values ('user2', 'user2', 'admin');


insert into mt_param_groups (param_group_code, param_group_name, created_by) values ('GNDR', 'GENDER', (select user_id from mt_users where user_name = 'admin'));

insert into mt_param_details (param_detail_code, param_detail_name, param_group_id, created_by) values ('GNDRM', 'MALE', (select param_group_id from mt_param_groups where param_group_code = 'GNDR'), (select user_id from mt_users where user_name = 'admin'));
insert into mt_param_details (param_detail_code, param_detail_name, param_group_id, created_by) values ('GNDRF', 'FEMALE', (select param_group_id from mt_param_groups where param_group_code = 'GNDR'), (select user_id from mt_users where user_name = 'admin'));