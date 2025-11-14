package sptech.school.CRUD.application.service.log;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.domain.entity.LogModel;
import sptech.school.CRUD.infrastructure.persistence.log.LogRepository;
import sptech.school.CRUD.interfaces.dto.Estoque.AtualizarInfoEstoqueDto;

@Service
@RequiredArgsConstructor
public class AtualizarInfoService {

    private final LogRepository logRepository;

    public LogModel postarLogInfo(AtualizarInfoEstoqueDto dto, String nome){

        LogModel log = new LogModel();

        log.setUsername(nome);
        log.setMensagem("O usuário " + nome + " atualizou as informações do  tipo de material: " + dto.getTipoMaterial() + ", e mudou o IPI para: " + dto.getIpi());
        log.setData(java.time.LocalDateTime.now());

        return logRepository.save(log);

    }

}
