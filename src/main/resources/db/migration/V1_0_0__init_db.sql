-- We need a sequence because the auto-generation of the id field is made with this.
-- The increment by 50 is because of a framework default value.
CREATE SEQUENCE  IF NOT EXISTS app_user_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE app_user (
  id BIGINT NOT NULL,
   name VARCHAR(255) NOT NULL,
   CONSTRAINT pk_app_user PRIMARY KEY (id)
);

ALTER TABLE app_user ADD CONSTRAINT uc_app_user_name UNIQUE (name);