package sptech.school.CRUD.service;

import org.springframework.stereotype.Service;
import sptech.school.CRUD.Model.SetorModel;
import sptech.school.CRUD.Repository.SetorRepository;

import java.util.List;

@Service
public class SetorService {

    private final SetorRepository setorRepository;

    public SetorService(SetorRepository setorRepository) {
        this.setorRepository = setorRepository;
    }

    public List<SetorModel> getAll(){return setorRepository.findAll();}

    public SetorModel cadastrarSetor(SetorModel setor){

        if(setor == null){
            return null;
        }

        return setorRepository.save(setor);}
}
