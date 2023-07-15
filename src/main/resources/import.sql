create table veiculo
(
    id        BIGINT PRIMARY KEY,
    veiculo   VARCHAR(255) NOT NULL,
    marca     VARCHAR(255) NOT NULL,
    cor       VARCHAR(255) NOT NULL,
    ano       INTEGER      NOT NULL,
    descricao TEXT,
    vendido   BOOLEAN      NOT NULL,
    created   TIMESTAMP    NOT NULL,
    updated   TIMESTAMP
);
