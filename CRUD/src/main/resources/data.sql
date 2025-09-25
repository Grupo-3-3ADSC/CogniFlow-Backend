-- 1. CARGOS - Inserir apenas se não existir
INSERT INTO cargo_model (created_at, nome, updated_at)
SELECT NOW(), 'comum', NOW()
WHERE NOT EXISTS (SELECT 1 FROM cargo_model WHERE nome = 'comum');

INSERT INTO cargo_model (created_at, nome, updated_at)
SELECT NOW(), 'gestor', NOW()
WHERE NOT EXISTS (SELECT 1 FROM cargo_model WHERE nome = 'gestor');

INSERT INTO cargo_model (created_at, nome, updated_at)
SELECT NOW(), 'Assistente de Compras', NOW()
WHERE NOT EXISTS (SELECT 1 FROM cargo_model WHERE nome = 'Assistente de Compras');

INSERT INTO cargo_model (created_at, nome, updated_at)
SELECT NOW(), 'Comprador Pleno', NOW()
WHERE NOT EXISTS (SELECT 1 FROM cargo_model WHERE nome = 'Comprador Pleno');

INSERT INTO cargo_model (created_at, nome, updated_at)
SELECT NOW(), 'Analista de Compras', NOW()
WHERE NOT EXISTS (SELECT 1 FROM cargo_model WHERE nome = 'Analista de Compras');

INSERT INTO cargo_model (created_at, nome, updated_at)
SELECT NOW(), 'Coordenador de Compras', NOW()
WHERE NOT EXISTS (SELECT 1 FROM cargo_model WHERE nome = 'Coordenador de Compras');

INSERT INTO cargo_model (created_at, nome, updated_at)
SELECT NOW(), 'Gerente de Suprimentos', NOW()
WHERE NOT EXISTS (SELECT 1 FROM cargo_model WHERE nome = 'Gerente de Suprimentos');

-- 2. USUÁRIO - Inserir apenas se não existir
INSERT INTO usuario_model (ativo, cargo_id, created_at, nome, email, password, updated_at)
SELECT 1, 2, NOW(), 'John Doe', 'john@doe.com', '$2a$10$0/TKTGxdREbWaWjWYhwf6e9P1fPOAMMNqEnZgOG95jnSkHSfkkIrC', NOW()
WHERE NOT EXISTS (SELECT 1 FROM usuario_model WHERE email = 'john@doe.com');

-- 3. FORNECEDORES - Inserir um por vez
INSERT INTO fornecedor_model (cnpj, nome_fantasia, razao_social)
SELECT '33611500000119', 'GERDAU', 'GERDAU AÇOS LONGOS SA'
WHERE NOT EXISTS (SELECT 1 FROM fornecedor_model WHERE cnpj = '33611500000119');

INSERT INTO fornecedor_model (cnpj, nome_fantasia, razao_social)
SELECT '60894730000105', 'USIMINAS', 'USINAS SIDERÚRGICAS DE MINAS GERAIS SA'
WHERE NOT EXISTS (SELECT 1 FROM fornecedor_model WHERE cnpj = '60894730000105');

INSERT INTO fornecedor_model (cnpj, nome_fantasia, razao_social)
SELECT '17469701000177', 'ARCELORMITTAL', 'ARCELORMITTAL BRASIL SA'
WHERE NOT EXISTS (SELECT 1 FROM fornecedor_model WHERE cnpj = '17469701000177');

INSERT INTO fornecedor_model (cnpj, nome_fantasia, razao_social)
SELECT '42566752000164', 'VILLARES METALS', 'VILLARES METALS SA'
WHERE NOT EXISTS (SELECT 1 FROM fornecedor_model WHERE cnpj = '42566752000164');

INSERT INTO fornecedor_model (cnpj, nome_fantasia, razao_social)
SELECT '33042730000104', 'CSN', 'CSN COMPANHIA SIDERÚRGICA NACIONAL'
WHERE NOT EXISTS (SELECT 1 FROM fornecedor_model WHERE cnpj = '33042730000104');

INSERT INTO fornecedor_model (cnpj, nome_fantasia, razao_social, cargo, responsavel, ie)
SELECT '11244042000194', 'SPTECH', 'SAO PAULO TECH TECNOLOGIA EDUCACIONAL LTDA', 'CEO', 'Vera', '567123547965'
WHERE NOT EXISTS (SELECT 1 FROM fornecedor_model WHERE cnpj = '11244042000194');

-- 4. ENDEREÇOS - Inserir um por vez
INSERT INTO endereco_model (cep, complemento, numero, fornecedor_id)
SELECT '13060722', 'Rua Professor Fernando Cúrcio, Parque Residencial Vila União, Campinas - SP', 5000, 1
WHERE NOT EXISTS (SELECT 1 FROM endereco_model WHERE fornecedor_id = 1);

INSERT INTO endereco_model (cep, complemento, numero, fornecedor_id)
SELECT '09330735', 'Rua Vicente Pagano, Jardim Olinda, Mauá - SP', 1000, 2
WHERE NOT EXISTS (SELECT 1 FROM endereco_model WHERE fornecedor_id = 2);

INSERT INTO endereco_model (cep, complemento, numero, fornecedor_id)
SELECT '04792000', 'Rua José Galdino da Silva, Interlagos, São Paulo - SP', 2000, 3
WHERE NOT EXISTS (SELECT 1 FROM endereco_model WHERE fornecedor_id = 3);

INSERT INTO endereco_model (cep, complemento, numero, fornecedor_id)
SELECT '04792000', 'Rua José Galdino da Silva, Interlagos, São Paulo - SP', 2000, 4
WHERE NOT EXISTS (SELECT 1 FROM endereco_model WHERE fornecedor_id = 4);

INSERT INTO endereco_model (cep, complemento, numero, fornecedor_id)
SELECT '08280040', 'Rua Isaar Carlos de Camargo, Cidade Líder, São Paulo - SP', 3000, 5
WHERE NOT EXISTS (SELECT 1 FROM endereco_model WHERE fornecedor_id = 5);

INSERT INTO endereco_model (cep, complemento, numero, fornecedor_id)
SELECT '01414001', 'Rua Haddock Lobo, Consolação, São Paulo - SP', 595, 6
WHERE NOT EXISTS (SELECT 1 FROM endereco_model WHERE fornecedor_id = 6);

-- 5. CONTATOS - Inserir um por vez
INSERT INTO contato_model (email, telefone, fornecedor_id)
SELECT 'gerdau@email.com', '11926595894', 1
WHERE NOT EXISTS (SELECT 1 FROM contato_model WHERE fornecedor_id = 1);

INSERT INTO contato_model (email, telefone, fornecedor_id)
SELECT 'usiminas@email.com', '11951616216', 2
WHERE NOT EXISTS (SELECT 1 FROM contato_model WHERE fornecedor_id = 2);

INSERT INTO contato_model (email, telefone, fornecedor_id)
SELECT 'arcelormitta@email.com', '11915161561', 3
WHERE NOT EXISTS (SELECT 1 FROM contato_model WHERE fornecedor_id = 3);

INSERT INTO contato_model (email, telefone, fornecedor_id)
SELECT 'villa@email.com', '11925648544', 4
WHERE NOT EXISTS (SELECT 1 FROM contato_model WHERE fornecedor_id = 4);

INSERT INTO contato_model (email, telefone, fornecedor_id)
SELECT 'csn@email.com', '11954456161', 5
WHERE NOT EXISTS (SELECT 1 FROM contato_model WHERE fornecedor_id = 5);

INSERT INTO contato_model (email, telefone, fornecedor_id)
SELECT 'sptech@email.com', '11999944444', 6
WHERE NOT EXISTS (SELECT 1 FROM contato_model WHERE fornecedor_id = 6);

-- 6. ESTOQUE - Inserir um por vez
INSERT INTO estoque_model (tipo_material, ipi, quantidade_minima, quantidade_maxima, quantidade_atual, ultima_movimentacao)
SELECT 'SAE 1020', 5, 250, 10000, 750, NOW()
WHERE NOT EXISTS (SELECT 1 FROM estoque_model WHERE tipo_material = 'SAE 1020');

INSERT INTO estoque_model (tipo_material, ipi, quantidade_minima, quantidade_maxima, quantidade_atual, ultima_movimentacao)
SELECT 'SAE 1045', 0, 300, 10000, 600, NOW()
WHERE NOT EXISTS (SELECT 1 FROM estoque_model WHERE tipo_material = 'SAE 1045');

INSERT INTO estoque_model (tipo_material, ipi, quantidade_minima, quantidade_maxima, quantidade_atual, ultima_movimentacao)
SELECT 'HARDOX 450', 3, 400, 10000, 7500, NOW()
WHERE NOT EXISTS (SELECT 1 FROM estoque_model WHERE tipo_material = 'HARDOX 450');

INSERT INTO estoque_model (tipo_material, ipi, quantidade_minima, quantidade_maxima, quantidade_atual, ultima_movimentacao)
SELECT 'ASTM A36', 2, 500, 10000, 1500, NOW()
WHERE NOT EXISTS (SELECT 1 FROM estoque_model WHERE tipo_material = 'ASTM A36');

INSERT INTO estoque_model (tipo_material, ipi, quantidade_minima, quantidade_maxima, quantidade_atual, ultima_movimentacao)
SELECT 'ASTM A283 Gr.C', 0, 600, 10000, 700, NOW()
WHERE NOT EXISTS (SELECT 1 FROM estoque_model WHERE tipo_material = 'ASTM A283 Gr.C');

INSERT INTO estoque_model (tipo_material, ipi, quantidade_minima, quantidade_maxima, quantidade_atual, ultima_movimentacao)
SELECT 'ASTM A285 Gr.C', 0, 550, 10000, 900, NOW()
WHERE NOT EXISTS (SELECT 1 FROM estoque_model WHERE tipo_material = 'ASTM A285 Gr.C');

INSERT INTO estoque_model (tipo_material, ipi, quantidade_minima, quantidade_maxima, quantidade_atual, ultima_movimentacao)
SELECT 'ASTM A516 gr70', 5, 650, 10000, 1200, NOW()
WHERE NOT EXISTS (SELECT 1 FROM estoque_model WHERE tipo_material = 'ASTM A516 gr70');

INSERT INTO estoque_model (tipo_material, ipi, quantidade_minima, quantidade_maxima, quantidade_atual, ultima_movimentacao)
SELECT 'SAC 350', 3, 700, 10000, 3000, NOW()
WHERE NOT EXISTS (SELECT 1 FROM estoque_model WHERE tipo_material = 'SAC 350');

INSERT INTO estoque_model (tipo_material, ipi, quantidade_minima, quantidade_maxima, quantidade_atual, ultima_movimentacao)
SELECT 'ASTM A572 Gr 50', 2, 500, 10000, 2200, NOW()
WHERE NOT EXISTS (SELECT 1 FROM estoque_model WHERE tipo_material = 'ASTM A572 Gr 50');

INSERT INTO estoque_model (tipo_material, ipi, quantidade_minima, quantidade_maxima, quantidade_atual, ultima_movimentacao)
SELECT 'S355 J2', 0, 450, 10000, 1800, NOW()
WHERE NOT EXISTS (SELECT 1 FROM estoque_model WHERE tipo_material = 'S355 J2');

INSERT INTO estoque_model (tipo_material, ipi, quantidade_minima, quantidade_maxima, quantidade_atual, ultima_movimentacao)
SELECT 'STRENX 700', 0, 200, 10000, 6000, NOW()
WHERE NOT EXISTS (SELECT 1 FROM estoque_model WHERE tipo_material = 'STRENX 700');

-- 7. TRANSFERÊNCIAS - Limpar e inserir novos dados
DELETE FROM transferencia_model WHERE setor IN ('C1', 'C2', 'C3', 'C4');

INSERT INTO transferencia_model (quantidade_transferida, setor, estoque_id, ultima_movimentacao)
VALUES
(100, 'C1', 4, NOW()),
(200, 'C2', 2, NOW()),
(150, 'C3', 1, NOW()),
(300, 'C4', 3, NOW()),
(200, 'C2', 2, NOW()),
(150, 'C3', 1, NOW()),
(300, 'C1', 3, NOW());

-- 8. ORDENS DE COMPRA - Inserir apenas se não existir
INSERT INTO ordem_de_compra_model (cond_pagamento, data_de_emissao, descricao_material, estoque_id, fornecedor_id, pendencia_alterada, prazo_entrega, quantidade, rastreabilidade, usuario_id, valor_kg, valor_peca, valor_unitario)
SELECT '30 dias', '2025-01-15 10:30:00.000000', 'Chapa 15x15', 2, 1, 0, '2025-01-25', 150, 'ABC123456789', 1, 15.0, 8.5, 12.75
WHERE NOT EXISTS (SELECT 1 FROM ordem_de_compra_model WHERE rastreabilidade = 'ABC123456789');

INSERT INTO ordem_de_compra_model (cond_pagamento, data_de_emissao, descricao_material, estoque_id, fornecedor_id, pendencia_alterada, prazo_entrega, quantidade, rastreabilidade, usuario_id, valor_kg, valor_peca, valor_unitario)
SELECT '45 dias', '2025-02-20 14:15:30.000000', 'Barra redonda 25mm', 4, 2, 1, '2025-03-05', 200, 'DEF987654321', 1, 18.5, 12.0, 16.25
WHERE NOT EXISTS (SELECT 1 FROM ordem_de_compra_model WHERE rastreabilidade = 'DEF987654321');

INSERT INTO ordem_de_compra_model (cond_pagamento, data_de_emissao, descricao_material, estoque_id, fornecedor_id, pendencia_alterada, prazo_entrega, quantidade, rastreabilidade, usuario_id, valor_kg, valor_peca, valor_unitario)
SELECT '60 dias', '2025-03-10 09:45:15.000000', 'Chapa 30x30', 3, 3, 0, '2025-03-20', 75, 'GHI456789123', 1, 22.0, 15.5, 19.80
WHERE NOT EXISTS (SELECT 1 FROM ordem_de_compra_model WHERE rastreabilidade = 'GHI456789123');

INSERT INTO ordem_de_compra_model (cond_pagamento, data_de_emissao, descricao_material, estoque_id, fornecedor_id, pendencia_alterada, prazo_entrega, quantidade, rastreabilidade, usuario_id, valor_kg, valor_peca, valor_unitario)
SELECT '30 dias', '2025-04-18 16:20:45.000000', 'Tubo quadrado 20x20', 7, 4, 1, '2025-04-28', 300, 'JKL789123456', 1, 14.75, 6.25, 11.90
WHERE NOT EXISTS (SELECT 1 FROM ordem_de_compra_model WHERE rastreabilidade = 'JKL789123456');

INSERT INTO ordem_de_compra_model (cond_pagamento, data_de_emissao, descricao_material, estoque_id, fornecedor_id, pendencia_alterada, prazo_entrega, quantidade, rastreabilidade, usuario_id, valor_kg, valor_peca, valor_unitario)
SELECT '45 dias', '2025-05-22 11:35:20.000000', 'Perfil L 50x50', 5, 5, 0, '2025-06-10', 120, 'MNO321654987', 1, 16.25, 9.75, 14.50
WHERE NOT EXISTS (SELECT 1 FROM ordem_de_compra_model WHERE rastreabilidade = 'MNO321654987');

INSERT INTO ordem_de_compra_model (cond_pagamento, data_de_emissao, descricao_material, estoque_id, fornecedor_id, pendencia_alterada, prazo_entrega, quantidade, rastreabilidade, usuario_id, valor_kg, valor_peca, valor_unitario)
SELECT '30 dias', '2025-06-14 13:50:10.000000', 'Chapa 25x25', 3, 4, 1, '2025-06-24', 180, 'PQR654987321', 1, 19.50, 11.25, 17.35
WHERE NOT EXISTS (SELECT 1 FROM ordem_de_compra_model WHERE rastreabilidade = 'PQR654987321');

INSERT INTO ordem_de_compra_model (cond_pagamento, data_de_emissao, descricao_material, estoque_id, fornecedor_id, pendencia_alterada, prazo_entrega, quantidade, rastreabilidade, usuario_id, valor_kg, valor_peca, valor_unitario)
SELECT '60 dias', '2025-07-08 08:25:35.000000', 'Barra chata 40x10', 5, 1, 0, '2025-07-25', 250, 'STU987321654', 1, 13.75, 7.80, 10.95
WHERE NOT EXISTS (SELECT 1 FROM ordem_de_compra_model WHERE rastreabilidade = 'STU987321654');

INSERT INTO ordem_de_compra_model (cond_pagamento, data_de_emissao, descricao_material, estoque_id, fornecedor_id, pendencia_alterada, prazo_entrega, quantidade, rastreabilidade, usuario_id, valor_kg, valor_peca, valor_unitario)
SELECT '30 dias', '2025-08-16 15:51:55.000000', 'Chapa 20x20', 1, 2, 1, '2025-08-28', 400, 'TUYTU77696969696', 1, 12.5, 5.0, 9.90
WHERE NOT EXISTS (SELECT 1 FROM ordem_de_compra_model WHERE rastreabilidade = 'TUYTU77696969696');

INSERT INTO ordem_de_compra_model (cond_pagamento, data_de_emissao, descricao_material, estoque_id, fornecedor_id, pendencia_alterada, prazo_entrega, quantidade, rastreabilidade, usuario_id, valor_kg, valor_peca, valor_unitario)
SELECT '45 dias', '2025-09-12 12:10:25.000000', 'Tubo redondo 32mm', 8, 3, 0, '2025-09-30', 90, 'VWX147258369', 1, 21.25, 13.50, 18.75
WHERE NOT EXISTS (SELECT 1 FROM ordem_de_compra_model WHERE rastreabilidade = 'VWX147258369');

INSERT INTO ordem_de_compra_model (cond_pagamento, data_de_emissao, descricao_material, estoque_id, fornecedor_id, pendencia_alterada, prazo_entrega, quantidade, rastreabilidade, usuario_id, valor_kg, valor_peca, valor_unitario)
SELECT '30 dias', '2025-10-25 17:40:50.000000', 'Perfil U 100x50', 6, 4, 1, '2025-11-05', 160, 'YZA369258147', 1, 17.80, 10.30, 15.60
WHERE NOT EXISTS (SELECT 1 FROM ordem_de_compra_model WHERE rastreabilidade = 'YZA369258147');

INSERT INTO ordem_de_compra_model (cond_pagamento, data_de_emissao, descricao_material, estoque_id, fornecedor_id, pendencia_alterada, prazo_entrega, quantidade, rastreabilidade, usuario_id, valor_kg, valor_peca, valor_unitario)
SELECT '60 dias', '2025-11-07 09:15:40.000000', 'Chapa 35x35', 7, 5, 0, '2025-11-20', 220, 'BCD258147369', 1, 20.75, 14.25, 19.25
WHERE NOT EXISTS (SELECT 1 FROM ordem_de_compra_model WHERE rastreabilidade = 'BCD258147369');

INSERT INTO ordem_de_compra_model (cond_pagamento, data_de_emissao, descricao_material, estoque_id, fornecedor_id, pendencia_alterada, prazo_entrega, quantidade, rastreabilidade, usuario_id, valor_kg, valor_peca, valor_unitario)
SELECT '45 dias', '2025-12-19 14:30:15.000000', 'Barra sextavada 15mm', 10, 1, 1, '2025-12-30', 350, 'EFG741852963', 1, 11.90, 4.75, 8.65
WHERE NOT EXISTS (SELECT 1 FROM ordem_de_compra_model WHERE rastreabilidade = 'EFG741852963');