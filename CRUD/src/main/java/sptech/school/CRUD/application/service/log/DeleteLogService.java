package sptech.school.CRUD.application.service.log;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.domain.entity.LogModel;
import sptech.school.CRUD.infrastructure.persistence.fornecedor.FornecedorRepository;
import sptech.school.CRUD.infrastructure.persistence.usuario.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class DeleteLogService {

    private final LogService logService;
    private final UsuarioRepository usuarioRepository;
    private final FornecedorRepository fornecedorRepository;

    public LogModel postarLogDelete(){

    }
}
