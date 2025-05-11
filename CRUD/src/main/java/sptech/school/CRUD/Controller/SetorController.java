package sptech.school.CRUD.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.CRUD.Model.SetorModel;
import sptech.school.CRUD.service.SetorService;

@RestController
@RequestMapping("/setores")
public class SetorController {

    private final SetorService setorService;

    public SetorController(SetorService setorService) {
        this.setorService = setorService;
    }

    @PostMapping
    public ResponseEntity<SetorModel> cadastrarSetor(@RequestBody SetorModel setor){

        SetorModel novoSetor = setorService.cadastrarSetor(setor);

        if(novoSetor == null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(201).body(novoSetor);

    }
}
