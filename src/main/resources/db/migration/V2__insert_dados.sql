INSERT INTO usuarios (nome, email, senha, role, cep, rua, bairro) VALUES
    ('Administrador', 'admin@foodly.com',
     '$2a$10$7QwFcANQUBh5b5JNtSDCreqvCESmPqfZXHo1iyh6VKyY.Y6r4f7i2',
     'ROLE_ADMIN', '01001-000', 'Praça da Sé', 'Sé');

INSERT INTO usuarios (nome, email, senha, role, cep, rua, bairro) VALUES
    ('João Restaurante', 'joao@foodly.com',
     '$2a$10$N0y5J9dP2y0KjbJkQkZl1.T8JrW5zKXFQAMqNfJGiGsBnqDG3Kzom',
     'ROLE_RESTAURANT_OWNER', '01310-100', 'Avenida Paulista', 'Bela Vista');

INSERT INTO usuarios (nome, email, senha, role, cep, rua, bairro) VALUES
    ('Maria Cliente', 'maria@foodly.com',
     '$2a$10$Hm3z5.KiQ9E8IYoNPwEWuuSJZMbqHR3DzHfNJP4GVm.NrLiDJSrMa',
     'ROLE_CUSTOMER', '04538-133', 'Rua Funchal', 'Vila Olímpia');

INSERT INTO categorias (nome) VALUES ('Lanches');
INSERT INTO categorias (nome) VALUES ('Bebidas');
INSERT INTO categorias (nome) VALUES ('Sobremesas');
INSERT INTO categorias (nome) VALUES ('Saudáveis');
INSERT INTO categorias (nome) VALUES ('Fast Food');
INSERT INTO categorias (nome) VALUES ('Japonesa');
INSERT INTO categorias (nome) VALUES ('Italiana');
INSERT INTO categorias (nome) VALUES ('Brasileira');

INSERT INTO restaurantes (nome, descricao, categoria, imagem_url, dono_id) VALUES
    ('Burger House', 'Os melhores burgers da cidade', 'Lanches',
     'https://via.placeholder.com/300x200?text=BurgerHouse', 2);

INSERT INTO restaurantes (nome, descricao, categoria, imagem_url, dono_id) VALUES
    ('Sushi Express', 'Culinária japonesa fresquinha', 'Japonesa',
     'https://via.placeholder.com/300x200?text=SushiExpress', 2);

INSERT INTO produtos (nome, descricao, preco, imagem_url, categoria_id, restaurante_id) VALUES
    ('Hambúrguer Clássico', 'Pão brioche, carne 180g, queijo e molho especial',
     25.90, 'https://via.placeholder.com/200?text=Burguer', 1, 1);

INSERT INTO produtos (nome, descricao, preco, imagem_url, categoria_id, restaurante_id) VALUES
    ('Bacon Burguer', 'Carne 180g, bacon crocante, cheddar',
     32.90, 'https://via.placeholder.com/200?text=Bacon', 1, 1);

INSERT INTO produtos (nome, descricao, preco, imagem_url, categoria_id, restaurante_id) VALUES
    ('Coca-Cola Lata', '350ml gelada',
     6.50, 'https://via.placeholder.com/200?text=Coca', 2, 1);

INSERT INTO produtos (nome, descricao, preco, imagem_url, categoria_id, restaurante_id) VALUES
    ('Combo Sushi 12 peças', 'Seleção do chef com salmão e atum',
     65.00, 'https://via.placeholder.com/200?text=Sushi', 6, 2);

INSERT INTO produtos (nome, descricao, preco, imagem_url, categoria_id, restaurante_id) VALUES
    ('Temaki de Salmão', 'Salmão fresco com cream cheese',
     28.00, 'https://via.placeholder.com/200?text=Temaki', 6, 2);