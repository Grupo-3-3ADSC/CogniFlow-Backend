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
import sptech.school.CRUD.dto.Fornecedor.FornecedorCompletoDTO;
import sptech.school.CRUD.dto.Fornecedor.FornecedorMapper;
import sptech.school.CRUD.exception.BadRequestException;
import sptech.school.CRUD.exception.ConflictException;

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

    @Autowired
    private ViaCepService viaCepService;

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    public FornecedorCadastroDto cadastroFornecedor(FornecedorCadastroDto fornecedorDto) {

        // Verificar se já existe fornecedor com o CNPJ
        if (fornecedorRepository.findByCnpj(fornecedorDto.getCnpj()).isPresent()) {
            throw new ConflictException("Já existe um fornecedor cadastrado com esse CNPJ.");
        }
        if (contatoRepository.existsByEmail(fornecedorDto.getEmail())) {
            throw new ConflictException("Já existe um fornecedor cadastrado com esse e-mail.");
       }

        try {
            viaCepService.buscarEnderecoPorCep(fornecedorDto.getCep());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("CEP inválido ou não encontrado.");
        }

        // Converte DTO para Model usando o mapper
        FornecedorModel fornecedor = FornecedorMapper.toCadastroModel(fornecedorDto);

        // Salva o fornecedor primeiro
        FornecedorModel fornecedorSalvo = fornecedorRepository.save(fornecedor);

        // Cria e salva o endereço
        EnderecoModel endereco = new EnderecoModel();
        endereco.setCep(fornecedorDto.getCep());
        endereco.setComplemento(fornecedorDto.getEndereco());
        endereco.setNumero(fornecedorDto.getNumero());
        endereco.setFornecedor(fornecedorSalvo);

        EnderecoModel enderecoSalvo = enderecoRepository.save(endereco);

        // Cria e salva o contato
        ContatoModel contato = new ContatoModel();
        contato.setTelefone(fornecedorDto.getTelefone());
        contato.setEmail(fornecedorDto.getEmail());
        contato.setFornecedor(fornecedorSalvo);

        ContatoModel contatoSalvo = contatoRepository.save(contato);

        // Retorna o DTO original com os dados salvos
        return fornecedorDto;
    }


    public  List<FornecedorCompletoDTO> fornecedorCompleto(){
        List<FornecedorCompletoDTO> fornecedores = fornecedorRepository.findFornecedoresCompletos();


        return fornecedores.stream()
                .map(FornecedorMapper::fornecedorCompleto)
                .collect(Collectors.toList());
    }

    public List<FornecedorCompletoDTO> buscarTop5Fornecedores() {
        List<Object[]> resultados = fornecedorRepository.findTop5FornecedoresCompletos();

        return resultados.stream().map(row -> {
            FornecedorCompletoDTO dto = new FornecedorCompletoDTO();
            dto.setFornecedorId((Integer) row[0]);
            dto.setCnpj((String) row[1]);
            dto.setRazaoSocial((String) row[2]);
            dto.setNomeFantasia((String) row[3]);
            dto.setEnderecoId((Integer) row[4]);
            dto.setCep((String) row[5]);
            dto.setNumero((Integer) row[6]);
            dto.setComplemento((String) row[7]);
            dto.setContatoId((Integer) row[8]);
            dto.setTelefone((String) row[9]);
            dto.setEmail((String) row[10]);
            return dto;
        }).collect(Collectors.toList());
    }

}
