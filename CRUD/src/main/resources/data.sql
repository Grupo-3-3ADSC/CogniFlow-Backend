# Use aqui caso não tenha mais nada no DataInitializer
#insert ignore into usuario_model
#   (ativo,cargo_id,created_at,nome, email, password,updated_at)
#values
#   (1,2,NOW(),'John Doe', 'john@doe.com', '$2a$10$0/TKTGxdREbWaWjWYhwf6e9P1fPOAMMNqEnZgOG95jnSkHSfkkIrC',NOW());

#INSERT IGNORE INTO estoque_model
 #   (tipoMaterial, quantidadeAtual, quantidadeMinima, quantidadeMaxima, ultimaMovimentacao)
#VALUES
 #   ('SAE 1020', 0, 0, 500, NOW());

-- INSERT IGNORE INTO ordem_de_compra_model (
--     cond_pagamento,
--     data_de_emissao,
--     descricao_material,
--     estoque_id,
--     fornecedor_id,
--     ipi,
--     pendencia_alterada,
--     prazo_entrega,
--     quantidade,
--     rastreabilidade,
--     usuario_id,
--     valor_kg,
--     valor_peca,
--     valor_unitario
-- ) VALUES
-- -- Janeiro 2025
-- ('30 dias', '2025-01-15 10:30:00.000000', 'Chapa 15x15', 1, 1, '10', '0', '2025-01-25', '150', 'ABC123456789', 1, '15.0', '8.5', '12.75'),
--
-- -- Fevereiro 2025
-- ('45 dias', '2025-02-20 14:15:30.000000', 'Barra redonda 25mm', 2, 2, '8', '1', '2025-03-05', '200', 'DEF987654321', 1, '18.5', '12.0', '16.25'),
--
-- -- Março 2025
-- ('60 dias', '2025-03-10 09:45:15.000000', 'Chapa 30x30', 3, 3, '15', '0', '2025-03-20', '75', 'GHI456789123', 1, '22.0', '15.5', '19.80'),
--
-- -- Abril 2025
-- ('30 dias', '2025-04-18 16:20:45.000000', 'Tubo quadrado 20x20', 1, 4, '12', '1', '2025-04-28', '300', 'JKL789123456', 1, '14.75', '6.25', '11.90'),
--
-- -- Maio 2025
-- ('45 dias', '2025-05-22 11:35:20.000000', 'Perfil L 50x50', 2, 5, '9', '0', '2025-06-10', '120', 'MNO321654987', 1, '16.25', '9.75', '14.50'),
--
-- -- Junho 2025
-- ('30 dias', '2025-06-14 13:50:10.000000', 'Chapa 25x25', 3, 6, '11', '1', '2025-06-24', '180', 'PQR654987321', 1, '19.50', '11.25', '17.35'),
--
-- -- Julho 2025
-- ('60 dias', '2025-07-08 08:25:35.000000', 'Barra chata 40x10', 1, 1, '13', '0', '2025-07-25', '250', 'STU987321654', 1, '13.75', '7.80', '10.95'),
--
-- -- Agosto 2025
-- ('30 dias', '2025-08-16 15:51:55.000000', 'Chapa 20x20', 2, 2, '12', '1', '2025-08-28', '400', 'TUYTU77696969696', 1, '12.5', '5.0', '9.90'),
--
-- -- Setembro 2025
-- ('45 dias', '2025-09-12 12:10:25.000000', 'Tubo redondo 32mm', 3, 3, '14', '0', '2025-09-30', '90', 'VWX147258369', 1, '21.25', '13.50', '18.75'),
--
-- -- Outubro 2025
-- ('30 dias', '2025-10-25 17:40:50.000000', 'Perfil U 100x50', 1, 4, '10', '1', '2025-11-05', '160', 'YZA369258147', 1, '17.80', '10.30', '15.60'),
--
-- -- Novembro 2025
-- ('60 dias', '2025-11-07 09:15:40.000000', 'Chapa 35x35', 2, 5, '16', '0', '2025-11-20', '220', 'BCD258147369', 1, '20.75', '14.25', '19.25'),
--
-- -- Dezembro 2025
-- ('45 dias', '2025-12-19 14:30:15.000000', 'Barra sextavada 15mm', 3, 6, '8', '1', '2025-12-30', '350', 'EFG741852963', 1, '11.90', '4.75', '8.65');