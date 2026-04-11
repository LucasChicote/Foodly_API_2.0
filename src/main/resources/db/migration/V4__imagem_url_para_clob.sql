ALTER TABLE restaurantes ADD imagem_url_novo CLOB;
UPDATE restaurantes SET imagem_url_novo = imagem_url;
ALTER TABLE restaurantes DROP COLUMN imagem_url;
ALTER TABLE restaurantes RENAME COLUMN imagem_url_novo TO imagem_url;

ALTER TABLE produtos ADD imagem_url_novo CLOB;
UPDATE produtos SET imagem_url_novo = imagem_url;
ALTER TABLE produtos DROP COLUMN imagem_url;
ALTER TABLE produtos RENAME COLUMN imagem_url_novo TO imagem_url;

COMMIT;