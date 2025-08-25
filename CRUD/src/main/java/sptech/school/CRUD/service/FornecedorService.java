package sptech.school.CRUD.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import sptech.school.CRUD.dto.Fornecedor.PaginacaoFornecedorDTO;
import sptech.school.CRUD.exception.RequisicaoConflitanteException;
import sptech.school.CRUD.exception.RequisicaoInvalidaException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;
    private final EnderecoRepository enderecoRepository;
    private final ContatoRepository contatoRepository;
    private final ViaCepService viaCepService;

    public FornecedorCadastroDto cadastroFornecedor(FornecedorCadastroDto fornecedorDto) {

        // Verificar se já existe fornecedor com o CNPJ
        if (fornecedorRepository.findByCnpj(fornecedorDto.getCnpj()).isPresent()) {
            throw new RequisicaoConflitanteException("Já existe um fornecedor cadastrado com esse CNPJ.");
        }
        if (contatoRepository.existsByEmail(fornecedorDto.getEmail())) {
            throw new RequisicaoConflitanteException("Já existe um fornecedor cadastrado com esse e-mail.");
       }
        if (fornecedorDto.getCnpj() == null || fornecedorDto.getCnpj().isBlank()){
            throw new RequisicaoInvalidaException("CNPJ não pode ser vazio nem nulo");
        }
        if (fornecedorDto.getCnpj().length() < 14){
            throw new RequisicaoInvalidaException("CNPJ deve conter pelo menos 14 dígitos");
        }

        try {
            viaCepService.buscarEnderecoPorCep(fornecedorDto.getCep());
        } catch (IllegalArgumentException ex) {
            throw new RequisicaoInvalidaException("CEP inválido ou não encontrado.");
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
        contato.setResponsavel(fornecedorDto.getResponsavel());
        contato.setCargo(fornecedorDto.getCargo());
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

    public PaginacaoFornecedorDTO fornecedorPaginado(Integer pagina, Integer tamanho){
        Pageable pageable = PageRequest.of(pagina, tamanho);

        Page<FornecedorCompletoDTO> fornecedores = fornecedorRepository.findFornecedoresPaginados(pageable);

        List<FornecedorCompletoDTO> fornecedoresMapeados= fornecedores.getContent()
                .stream()
                .map(FornecedorMapper::fornecedorCompleto)
                .collect(Collectors.toList());

        PaginacaoFornecedorDTO response = new PaginacaoFornecedorDTO();
        response.setData(fornecedoresMapeados);
        response.setPaginaAtual(pagina);
        response.setPaginasTotais(fornecedores.getTotalPages());
        response.setTotalItems(fornecedores.getTotalElements());
        response.setHasNext(fornecedores.hasNext());
        response.setHasPrevious(fornecedores.hasPrevious());
        response.setPageSize(tamanho);

        return response;
    }

}
