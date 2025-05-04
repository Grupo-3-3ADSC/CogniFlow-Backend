insert ignore into usuario_model
    (ativo,cargo_id,created_at,nome, email, password,updated_at)
values
    (1,2,NOW(),'John Doe', 'john@doe.com', '$2a$10$0/TKTGxdREbWaWjWYhwf6e9P1fPOAMMNqEnZgOG95jnSkHSfkkIrC',NOW());