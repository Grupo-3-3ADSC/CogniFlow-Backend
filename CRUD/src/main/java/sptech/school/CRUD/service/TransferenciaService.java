package sptech.school.CRUD.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.Repository.EstoqueRepository;
import sptech.school.CRUD.Repository.TransferenciaRepository;
import sptech.school.CRUD.dto.Transferencia.TransferenciaDto;
import sptech.school.CRUD.dto.Transferencia.TransferenciaMapper;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TransferenciaService {

    @Autowired
    private final TransferenciaRepository transferenciaRepository;

    public List<TransferenciaDto> buscarTipoTransferencia(){
        return transferenciaRepository.findAll().stream()
                .map(TransferenciaMapper::toTransferenciaDto)
                .collect(Collectors.toList());
    }
}
