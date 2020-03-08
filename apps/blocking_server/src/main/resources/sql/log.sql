CREATE TABLE "log"
(
    id        BIGSERIAL PRIMARY KEY,
    client    VARCHAR(255) NOT NULL,
    operation VARCHAR(255) NOT NULL,
    entityId  BIGINT NOT NULL
)