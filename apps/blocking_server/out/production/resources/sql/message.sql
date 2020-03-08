CREATE TABLE "message"
(
    id      BIGSERIAL PRIMARY KEY,
    "from"  BIGINT        NOT NULL REFERENCES "user" (id),
    "to"    BIGINT        NOT NULL REFERENCES "user" (id),
    message VARCHAR(1024) NOT NULL
)
