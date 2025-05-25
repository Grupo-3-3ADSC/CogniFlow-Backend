package sptech.school.CRUD.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.Model.EstoqueModel;
import sptech.school.CRUD.Repository.EstoqueRepository;
import sptech.school.CRUD.dto.Estoque.EstoqueListagemDto;
import sptech.school.CRUD.dto.Estoque.EstoqueMapper;

import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Service
public class EstoqueService {

    @Autowired
    private final EstoqueRepository estoqueRepository;


    public List<EstoqueModel> buscarEstoque(){
        return estoqueRepository.findAll();
    }


    public EstoqueModel cadastroEstoque(EstoqueModel estoque){
        return estoqueRepository.save(estoque);
    }





}
