CREATE TABLE product
(
    id           BIGINT NOT NULL PRIMARY KEY,
    name         VARCHAR(255),
    type_product VARCHAR(255),
    count        INTEGER,
    barcode      VARCHAR(255),
    created_at   DATE,
    updated_at   DATE
);