package sptech.school.CRUD.application.service.fornecedor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.application.service.ViaCepService;
import sptech.school.CRUD.domain.entity.ContatoModel;
import sptech.school.CRUD.domain.entity.EnderecoModel;
import sptech.school.CRUD.domain.entity.FornecedorModel;
import sptech.school.CRUD.domain.exception.RequisicaoConflitanteException;
import sptech.school.CRUD.domain.exception.RequisicaoInvalidaException;
import sptech.school.CRUD.infrastructure.persistence.contato.ContatoRepository;
import sptech.school.CRUD.infrastructure.persistence.endereco.EnderecoRepository;
import sptech.school.CRUD.infrastructure.persistence.fornecedor.FornecedorRepository;
import sptech.school.CRUD.interfaces.dto.Fornecedor.FornecedorCadastroDto;
import sptech.school.CRUD.interfaces.dto.Fornecedor.FornecedorMapper;

@Service
@RequiredArgsConstructor
public class CadastroFornecedorService {

    private final EnderecoRepository enderecoRepository;
    private final ContatoRepository contatoRepository;
    private final ViaCepService viaCepService;
    private final FornecedorRepository fornecedorRepository;

    public FornecedorCadastroDto cadastroFornecedor(FornecedorCadastroDto fornecedorDto) {

        // Verificar se já existe fornecedor com o CNPJ
        if (fornecedorRepository.findByCnpj(fornecedorDto.getCnpj()).isPresent()) {
            throw new RequisicaoConflitanteException("Já existe um fornecedor cadastrado com esse CNPJ.");
        }
        if (fornecedorRepository.findByCnpj(fornecedorDto.getIe()).isPresent()) {
            throw new RequisicaoConflitanteException("Já existe um fornecedor cadastrado com esse IE.");
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
}
