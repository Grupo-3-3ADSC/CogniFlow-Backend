package sptech.school.CRUD.interfaces.Controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.CRUD.application.service.log.LogService;
import sptech.school.CRUD.domain.entity.LogModel;
import sptech.school.CRUD.interfaces.dto.Log.LogDto;
import sptech.school.CRUD.interfaces.dto.Log.LogMapper;

import java.util.List;

@RequestMapping("/logs")
@RestController
@RequiredArgsConstructor
public class LogController {

    @Autowired
    private final LogService logService;

    @GetMapping("/todos")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<LogDto>> listarTodos(){
        return ResponseEntity.ok(logService.getAll());
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<LogDto> listarPorId(Integer id){
        LogModel log = logService.getLogById(id);
        return ResponseEntity.ok(LogMapper.toDto(log));
    }
}
