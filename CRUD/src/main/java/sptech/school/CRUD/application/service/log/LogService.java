package sptech.school.CRUD.application.service.log;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.domain.entity.LogModel;
import sptech.school.CRUD.infrastructure.persistence.log.LogRepository;
import sptech.school.CRUD.interfaces.dto.Log.LogDto;
import sptech.school.CRUD.interfaces.dto.Log.LogMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    public List<LogDto> getAll(){
        return logRepository.findAll()
                .stream()
                .map(LogMapper::toDto)
                .collect(Collectors.toList());
    }

    public LogModel getLogById(Integer id){
        return logRepository.getById(id);
    }
}
