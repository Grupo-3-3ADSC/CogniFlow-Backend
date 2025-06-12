package sptech.school.CRUD.service;

import org.springframework.stereotype.Service;
import sptech.school.CRUD.Model.FornecedorModel;
import sptech.school.CRUD.Repository.FornecedorRepository;
import sptech.school.CRUD.dto.Fornecedor.FornecedorCadastroDto;
import sptech.school.CRUD.dto.Fornecedor.FornecedorListagemDto;
import sptech.school.CRUD.dto.Fornecedor.FornecedorMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }
    public FornecedorModel cadastroFornecedor(FornecedorCadastroDto fornecedorDto) {
        // Converte DTO para Model usando o mapper
        FornecedorModel fornecedor = FornecedorMapper.toCadastroModel(fornecedorDto);

        // Salva no banco
        return fornecedorRepository.save(fornecedor);
    }

    public List<FornecedorModel> getAll(){return fornecedorRepository.findAll();}

    public  List<FornecedorListagemDto> listarFornecedoresDto(){

        List<FornecedorModel> fornecedores = fornecedorRepository.findAll();

        return fornecedores.stream()
                .map(FornecedorMapper::toListagemDto)
                .collect(Collectors.toList());
    }
}
