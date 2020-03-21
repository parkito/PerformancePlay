CREATE TABLE "user"
(
    id   INTEGER AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age  INTEGER      NOT NULL
);

CREATE TABLE "message"
(
    id      INTEGER AUTO_INCREMENT PRIMARY KEY,
    "from"  BIGINT        NOT NULL REFERENCES "user" (id),
    "to"    BIGINT        NOT NULL REFERENCES "user" (id),
    message VARCHAR(1024) NOT NULL
);

CREATE TABLE "LOG"
(
    id        INTEGER AUTO_INCREMENT PRIMARY KEY,
    client    VARCHAR(255) NOT NULL,
    operation VARCHAR(255) NOT NULL,
    entityId  BIGINT       NOT NULL
);
