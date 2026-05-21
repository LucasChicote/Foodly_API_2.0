ALTER TABLE produtos
    ADD (preco_promocional NUMBER(10,2) NULL);

ALTER TABLE produtos
    ADD (is_kit_sustentavel NUMBER(1) DEFAULT 0 NOT NULL);

ALTER TABLE produtos
    ADD (data_expiracao TIMESTAMP NULL);

CREATE INDEX idx_produtos_kit_expiracao
    ON produtos (is_kit_sustentavel, data_expiracao);