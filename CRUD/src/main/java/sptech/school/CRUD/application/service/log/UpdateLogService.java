package sptech.school.CRUD.application.service.log;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.domain.entity.LogModel;
import sptech.school.CRUD.infrastructure.persistence.log.LogRepository;

@Service
@RequiredArgsConstructor
public class UpdateLogService {

    private final LogRepository logRepository;

    public LogModel postarLogAoEditarIpi(){
        return null;
    }
}
