package sptech.school.CRUD.application.service.estoque;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.domain.entity.EstoqueModel;
import sptech.school.CRUD.infrastructure.persistence.estoque.EstoqueRepository;
import sptech.school.CRUD.domain.exception.RecursoNaoEncontradoException;
import sptech.school.CRUD.interfaces.dto.Estoque.*;


import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Service
@Transactional
public class EstoqueService {

    @Autowired
    private final EstoqueRepository estoqueRepository;


    public List<EstoqueListagemDto> buscarEstoque() {
        return estoqueRepository.findAll().stream()
                .map(EstoqueMapper::toListagemDto)
                .collect(Collectors.toList());
    }

    public EstoqueListagemDto buscarMaterialPorId(int id) {
        EstoqueModel estoque = estoqueRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Material não encontrado com ID: " + id));
        return EstoqueMapper.toListagemDto(estoque);
    }
}
