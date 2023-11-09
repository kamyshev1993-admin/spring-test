create table client
(
    ID         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    FIRST_NAME VARCHAR(255)        NOT NULL,
    LAST_NAME  VARCHAR(255)        NOT NULL,
    EMAIL      varchar(100) UNIQUE NOT NULL,
    PASSWORD   VARCHAR(255)        NOT NULL,
    ROLE       varchar(20)         NOT NULL
)