package sptech.school.CRUD.application.service.log;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.domain.entity.CargoModel;
import sptech.school.CRUD.domain.entity.LogModel;
import sptech.school.CRUD.infrastructure.persistence.cargo.CargoRepository;
import sptech.school.CRUD.infrastructure.persistence.log.LogRepository;
import sptech.school.CRUD.interfaces.dto.Usuario.UsuarioCadastroDto;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CadastroGestorLogService {

    private final LogRepository logRepository;
    private final CargoRepository cargoRepository;

    public LogModel postarLogCadastroUsuario(UsuarioCadastroDto usuario, String nome){
        LogModel log = new LogModel();

        Optional<CargoModel> cargo = cargoRepository.findById(usuario.getCargoId());

        log.setUsername(nome);
        log.setMensagem("O usuário " + nome + " cadastrou o usuário " + usuario.getNome() + " com o cargo " + cargo.get().getNome());
        log.setData(java.time.LocalDateTime.now());

        return logRepository.save(log);
    }
}
