-- DROP TABLE IF EXISTS departments CASCADE;
-- DROP TABLE IF EXISTS employees CASCADE;
-- DROP TABLE IF EXISTS accounts CASCADE;
--DROP SEQUENCE IF EXISTS department_id_seq;
--DROP SEQUENCE IF EXISTS employee_id_seq;
--DROP SEQUENCE IF EXISTS account_id_seq;

-- CREATE SEQUENCE department_id_seq START WITH 1;
-- CREATE SEQUENCE employee_id_seq START WITH 1;
-- CREATE SEQUENCE account_id_seq START WITH 1;


CREATE TABLE departments (
    /*id                INTEGER NOT NULL default nextval('department_id_seq'), */
    id                SERIAL NOT NULL,
    name              VARCHAR(30) not null unique,
    description       VARCHAR(150),
    location          VARCHAR(100)
);

ALTER TABLE departments ADD CONSTRAINT department_pk PRIMARY KEY ( id );

CREATE TABLE employees (
    /*id              INTEGER NOT NULL default nextval('employee_id_seq'),*/
    id              SERIAL NOT NULL,
    name            VARCHAR(30) not null unique,
    first_name      VARCHAR(30),
    last_name       VARCHAR(30),
    email           VARCHAR(50),
    address         VARCHAR(150),
    hired_date      date default CURRENT_DATE,
    department_id   INTEGER NOT NULL
);

ALTER TABLE employees ADD CONSTRAINT employee_pk PRIMARY KEY ( id );

CREATE TABLE accounts (
    /*id             INTEGER NOT NULL default nextval('account_id_seq'),*/
    id             SERIAL NOT NULL,
    account_type   VARCHAR(30),
    balance        NUMERIC(10, 2),
    create_date    date default CURRENT_DATE,
    employee_id    INTEGER NOT NULL
);

ALTER TABLE accounts ADD CONSTRAINT account_pk PRIMARY KEY ( id );

ALTER TABLE accounts
    ADD CONSTRAINT account_employee_fk FOREIGN KEY ( employee_id )
        REFERENCES employees ( id );

ALTER TABLE employees
    ADD CONSTRAINT employee_department_fk FOREIGN KEY ( department_id )
        REFERENCES departments ( id );

CREATE TABLE users (
    name            VARCHAR(30) NOT NULL PRIMARY KEY,
    first_name      VARCHAR(30),
    last_name       VARCHAR(30),
    email           VARCHAR(50)
);