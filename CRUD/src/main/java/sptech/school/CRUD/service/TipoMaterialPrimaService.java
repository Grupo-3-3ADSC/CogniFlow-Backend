package sptech.school.CRUD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.Model.TipoMateriaPrimaModel;
import sptech.school.CRUD.Repository.TipoMaterialPrimaRepository;

import java.util.List;

@Service
public class TipoMaterialPrimaService {

    @Autowired
    private TipoMaterialPrimaRepository repository;


        public List<TipoMateriaPrimaModel> buscarTodosMateriais() {
            return repository.findAll();
        }


}
