package sptech.school.CRUD.application.service.estoque;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.domain.entity.EstoqueModel;
import sptech.school.CRUD.domain.exception.RecursoNaoEncontradoException;
import sptech.school.CRUD.domain.exception.RequisicaoInvalidaException;
import sptech.school.CRUD.infrastructure.persistence.estoque.EstoqueRepository;
import sptech.school.CRUD.interfaces.dto.Estoque.AtualizarEstoqueDto;
import sptech.school.CRUD.interfaces.dto.Estoque.AtualizarInfoEstoqueDto;
import sptech.school.CRUD.interfaces.dto.Estoque.EstoqueListagemDto;
import sptech.school.CRUD.interfaces.dto.Estoque.EstoqueMapper;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AtualizarEstoqueService {

    @Autowired
    private final EstoqueRepository estoqueRepository;

    public EstoqueListagemDto atualizarEstoque(AtualizarEstoqueDto dto) {
        String tipoMaterial = dto.getTipoMaterial();
        Integer quantidade = dto.getQuantidadeAtual();

        // Validação: garantir que o tipoMaterial seja um dos pré-cadastrados
        EstoqueModel estoque = estoqueRepository.findByTipoMaterial(tipoMaterial)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Tipo de material não encontrado: " + tipoMaterial
                ));

        Integer novaQuantidade = estoque.getQuantidadeAtual() + quantidade;
        Integer max = estoque.getQuantidadeMaxima();
        Integer min = estoque.getQuantidadeMinima();

        if (max != null && novaQuantidade > max) {
            throw new RequisicaoInvalidaException("Quantidade Atual excede a Quantidade Máxima permitida");
        }
        if (min != null && novaQuantidade < min) {
            throw new RequisicaoInvalidaException("Quantidade Atual está abaixo da Quantidade Mínima permitida");
        }

        estoque.setQuantidadeAtual(novaQuantidade);
        estoque.setUltimaMovimentacao(LocalDateTime.now());

        EstoqueModel salvo = estoqueRepository.save(estoque);
        return EstoqueMapper.toListagemDto(salvo);
    }
    public EstoqueListagemDto atualizarInfo(AtualizarInfoEstoqueDto dto){

        Double ipi = dto.getIpi();
        String tipoMaterial = dto.getTipoMaterial();

        if(ipi == null || ipi.isNaN() || ipi < 0 ){
            throw  new RequisicaoInvalidaException("Ipi não pode ser nulo, NaN ou menor que zero");
        }

        EstoqueModel estoque = estoqueRepository.findByTipoMaterial(tipoMaterial)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Material não encontrado no estoque: " + tipoMaterial));

        estoque.setIpi(ipi);
        EstoqueModel atualizado = estoqueRepository.save(estoque);

        return EstoqueMapper.toListagemDto(atualizado);
    }
}
