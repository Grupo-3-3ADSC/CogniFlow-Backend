package sptech.school.CRUD.application.service.fornecedor;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.infrastructure.persistence.fornecedor.FornecedorRepository;
import sptech.school.CRUD.interfaces.dto.Fornecedor.FornecedorCompletoDTO;
import sptech.school.CRUD.interfaces.dto.Fornecedor.FornecedorMapper;
import sptech.school.CRUD.interfaces.dto.Fornecedor.PaginacaoFornecedorDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaginacaoFornecedorService {

    private final FornecedorRepository fornecedorRepository;

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
