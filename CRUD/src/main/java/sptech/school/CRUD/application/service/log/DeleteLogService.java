package sptech.school.CRUD.application.service.log;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.domain.entity.EstoqueModel;
import sptech.school.CRUD.domain.entity.FornecedorModel;
import sptech.school.CRUD.domain.entity.LogModel;
import sptech.school.CRUD.domain.entity.UsuarioModel;
import sptech.school.CRUD.domain.exception.RecursoNaoEncontradoException;
import sptech.school.CRUD.infrastructure.persistence.estoque.EstoqueRepository;
import sptech.school.CRUD.infrastructure.persistence.fornecedor.FornecedorRepository;
import sptech.school.CRUD.infrastructure.persistence.log.LogRepository;
import sptech.school.CRUD.infrastructure.persistence.usuario.UsuarioRepository;
import sptech.school.CRUD.interfaces.dto.Fornecedor.FornecedorCompletoDTO;
import sptech.school.CRUD.interfaces.dto.Log.LogDto;
import sptech.school.CRUD.interfaces.dto.Log.LogMapper;
import sptech.school.CRUD.interfaces.dto.Usuario.UsuarioDeleteDto;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeleteLogService {

    private final LogRepository logRepository;

    public LogModel postarLogDeleteUsuario(Optional<UsuarioModel> entity, String nome){

        LogModel log = new LogModel();

        log.setUsername(nome);
        log.setData(LocalDateTime.now());

        if(entity.isPresent()) {
            log.setMensagem("O usuário " + nome + " Deletou o Usuário " + entity.get().getNome());
        }
        else {
            throw new RecursoNaoEncontradoException("Usuário não encontrado");
        }

        return logRepository.save(log);
    }

    public LogModel postarLogDeleteFornecedor(Optional<FornecedorModel> entity, String nome){

        LogModel log = new LogModel();

        log.setUsername(nome);
        log.setData(LocalDateTime.now());

        if (entity.isPresent()){
            log.setMensagem("O usuário " + nome + " Deletou o Fornecedor " + entity.get().getNomeFantasia());
        }
        else {
            throw new RecursoNaoEncontradoException("Fornecedor não encontrado");
        }


        return logRepository.save(log);
    }
}
