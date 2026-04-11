ALTER TABLE restaurantes
DROP CONSTRAINT fk_restaurante_dono;

ALTER TABLE restaurantes
    ADD CONSTRAINT fk_restaurante_dono
        FOREIGN KEY (dono_id) REFERENCES usuarios(id)
            ON DELETE CASCADE;

ALTER TABLE pedidos
DROP CONSTRAINT fk_pedido_cliente;

ALTER TABLE pedidos
    ADD CONSTRAINT fk_pedido_cliente
        FOREIGN KEY (cliente_id) REFERENCES usuarios(id)
            ON DELETE CASCADE;

ALTER TABLE itens_pedido
DROP CONSTRAINT fk_item_pedido;

ALTER TABLE itens_pedido
    ADD CONSTRAINT fk_item_pedido
        FOREIGN KEY (pedido_id) REFERENCES pedidos(id)
            ON DELETE CASCADE;

COMMIT;