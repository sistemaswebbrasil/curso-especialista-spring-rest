#Cozinha 
insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');

#Restaurante
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Thai Gourmet', 10, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Thai Delivery', 9.50, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Tuk Tuk Comida Indiana', 15, 2);

#Estado
INSERT INTO estado (nome) VALUES ('Rio de Janeiro');
INSERT INTO estado (nome) VALUES ('São Paulo');
INSERT INTO estado (nome) VALUES ('Minas Gerais');

#Cidade
INSERT INTO cidade (nome, estado_id) VALUES ('Macaé', '1');
INSERT INTO cidade (nome, estado_id) VALUES ('Santos', '2');
INSERT INTO cidade (nome, estado_id) VALUES ('Juiz de Fora', '3');

#Permissão
INSERT INTO permissao (descricao, nome) VALUES ('Permite a criação , alteração , exclusão ', 'alteracao');
INSERT INTO permissao (descricao, nome) VALUES ('Permite somente visualização', 'visualizacao');

#Forma Pagamento
INSERT INTO forma_pagamento (descricao) VALUES ('Dinheiro');
INSERT INTO forma_pagamento (descricao) VALUES ('Cartão de Crédito');
INSERT INTO forma_pagamento (descricao) VALUES ('Cartão Débito');
INSERT INTO forma_pagamento (descricao) VALUES ('Boleto Bancário');
