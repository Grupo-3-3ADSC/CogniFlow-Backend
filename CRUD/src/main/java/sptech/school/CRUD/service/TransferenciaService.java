package sptech.school.CRUD.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.Repository.EstoqueRepository;

@RequiredArgsConstructor
@Service
public class TransferenciaService {

    @Autowired
    private final EstoqueRepository estoqueRepository;
}
