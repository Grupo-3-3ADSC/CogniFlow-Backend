package sptech.school.CRUD.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.Model.EstoqueModel;
import sptech.school.CRUD.Repository.EstoqueRepository;
import sptech.school.CRUD.dto.Estoque.EstoqueListagemDto;
import sptech.school.CRUD.dto.Estoque.EstoqueMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;


    public List<EstoqueModel> getAll(){
        return estoqueRepository.findAll();
    }
    public List<EstoqueListagemDto> listagemEstoqueDtos(){

        List<EstoqueModel> estoques = estoqueRepository.findAll();

        return estoques.stream()
                .map(EstoqueMapper::toListagemDto)
                .collect(Collectors.toList());
    }

    public EstoqueModel cadastroEstoque(EstoqueModel estoque){

        return estoqueRepository.save(estoque);
    }





}
