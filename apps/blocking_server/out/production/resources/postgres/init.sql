CREATE TABLE "user"
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age  INTEGER      NOT NULL
);

CREATE TABLE "message"
(
    id      BIGSERIAL PRIMARY KEY,
    "from"  BIGINT        NOT NULL REFERENCES "user" (id),
    "to"    BIGINT        NOT NULL REFERENCES "user" (id),
    message VARCHAR(1024) NOT NULL
);

CREATE TABLE "log"
(
    id        BIGSERIAL PRIMARY KEY,
    client    VARCHAR(255) NOT NULL,
    operation VARCHAR(255) NOT NULL,
    entityId  BIGINT       NOT NULL
);
