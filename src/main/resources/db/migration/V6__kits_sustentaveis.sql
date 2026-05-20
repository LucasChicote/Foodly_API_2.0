ALTER TABLE produtos
    ADD (
    preco_promocional     NUMBER(10,2),
    data_expiracao        TIMESTAMP,
    is_kit_sustentavel    NUMBER(1) DEFAULT 0,
    co2_economizado_kg    NUMBER(10,2) DEFAULT 0
);