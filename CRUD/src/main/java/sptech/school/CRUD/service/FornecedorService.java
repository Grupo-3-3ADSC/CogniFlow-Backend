package sptech.school.CRUD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.Model.ContatoModel;
import sptech.school.CRUD.Model.EnderecoModel;
import sptech.school.CRUD.Model.FornecedorModel;
import sptech.school.CRUD.Repository.ContatoRepository;
import sptech.school.CRUD.Repository.EnderecoRepository;
import sptech.school.CRUD.Repository.FornecedorRepository;
import sptech.school.CRUD.dto.Fornecedor.FornecedorCadastroDto;
import sptech.school.CRUD.dto.Fornecedor.FornecedorListagemDto;
import sptech.school.CRUD.dto.Fornecedor.FornecedorMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FornecedorService {

    @Autowired
    private final FornecedorRepository fornecedorRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ContatoRepository contatoRepository;

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }
    public FornecedorModel cadastroFornecedor(FornecedorCadastroDto fornecedorDto) {
        // Converte DTO para Model usando o mapper
        FornecedorModel fornecedor = FornecedorMapper.toCadastroModel(fornecedorDto);

        // Salva no banco


        EnderecoModel endereco = new EnderecoModel();
        endereco.setCep(fornecedorDto.getCep());
        endereco.setComplemento(fornecedorDto.getEndereco());

        // Converter número para Integer
        try {
            endereco.setNumero(Integer.parseInt(fornecedorDto.getNumero()));
        } catch (NumberFormatException e) {
            endereco.setNumero(null);
        }

        FornecedorModel fornecedorSalvo = fornecedorRepository.save(fornecedor);

        endereco.setFornecedor(fornecedorSalvo);
        EnderecoModel enderecoSalvo = enderecoRepository.save(endereco);

        ContatoModel contato = new ContatoModel();
        contato.setTelefone(fornecedorDto.getTelefone());
        contato.setEmail(fornecedorDto.getEmail());


        contato.setFornecedor(fornecedorSalvo);
        ContatoModel contatoSalvo = contatoRepository.save(contato);

        return fornecedorSalvo;
    }

    public List<FornecedorModel> getAll(){return fornecedorRepository.findAll();}

    public  List<FornecedorListagemDto> listarFornecedoresDto(){

        List<FornecedorModel> fornecedores = fornecedorRepository.findAll();

        return fornecedores.stream()
                .map(FornecedorMapper::toListagemDto)
                .collect(Collectors.toList());
    }


}
