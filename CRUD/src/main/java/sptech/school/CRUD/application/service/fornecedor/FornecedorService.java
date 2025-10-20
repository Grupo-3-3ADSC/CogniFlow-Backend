package sptech.school.CRUD.application.service.fornecedor;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sptech.school.CRUD.application.service.ViaCepService;
import sptech.school.CRUD.domain.entity.ContatoModel;
import sptech.school.CRUD.domain.entity.EnderecoModel;
import sptech.school.CRUD.domain.entity.FornecedorModel;
import sptech.school.CRUD.domain.entity.OrdemDeCompraModel;
import sptech.school.CRUD.domain.repository.ContatoRepository;
import sptech.school.CRUD.domain.repository.EnderecoRepository;
import sptech.school.CRUD.domain.repository.FornecedorRepository;
import sptech.school.CRUD.domain.repository.OrdemDeCompraRepository;
import sptech.school.CRUD.interfaces.dto.Fornecedor.FornecedorCadastroDto;
import sptech.school.CRUD.infrastructure.persistence.fornecedor.FornecedorRepository;
import sptech.school.CRUD.interfaces.dto.Fornecedor.FornecedorCompletoDTO;
import sptech.school.CRUD.interfaces.dto.Fornecedor.FornecedorMapper;
import sptech.school.CRUD.interfaces.dto.Fornecedor.PaginacaoFornecedorDTO;
import sptech.school.CRUD.domain.exception.RequisicaoConflitanteException;
import sptech.school.CRUD.domain.exception.RequisicaoInvalidaException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;
    private final EnderecoRepository enderecoRepository;
    private final ContatoRepository contatoRepository;
    private final ViaCepService viaCepService;


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

    @Transactional
    public Optional<FornecedorCompletoDTO> deletarFornecedor(Integer id){
        Optional<FornecedorModel> fornecedorOpt = fornecedorRepository.findById(id);

        if (fornecedorOpt.isEmpty()){
            return Optional.empty();
        }

        FornecedorModel fornecedor = fornecedorOpt.get();

        // Cria o DTO antes da deleção
        FornecedorCompletoDTO dto = FornecedorCompletoDTO.builder()
                .fornecedorId(fornecedor.getId())
                .cnpj(fornecedor.getCnpj())
                .ie(fornecedor.getIe())
                .razaoSocial(fornecedor.getRazaoSocial())
                .nomeFantasia(fornecedor.getNomeFantasia())
                .responsavel(fornecedor.getResponsavel())
                .cargo(fornecedor.getCargo())
                .build();

        // Primeiro, deletar filhos
        enderecoRepository.deleteEnderecosByFornecedorId(id);
        contatoRepository.deleteContatosByFornecedorId(id);
        ordemDeCompraRepository.deleteByFornecedorId(id);
        // Depois, deletar o fornecedor
        fornecedorRepository.delete(fornecedor);

        return Optional.of(dto);
    }

}
