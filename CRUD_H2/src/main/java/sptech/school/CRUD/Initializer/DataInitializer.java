package sptech.school.CRUD.Initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import sptech.school.CRUD.Model.CargoModel;
import sptech.school.CRUD.Model.UsuarioModel;
import sptech.school.CRUD.Repository.CargoRepository;
import sptech.school.CRUD.Repository.UsuarioRepository;

@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public void run(String... args) throws Exception {

        if(cargoRepository.count() == 0) {

            CargoModel comum = new CargoModel();
            comum.setNome("comum");
            cargoRepository.save(comum);

            CargoModel gestor = new CargoModel();
            gestor.setNome("gestor");
            cargoRepository.save(gestor);
        }

        if(usuarioRepository.count() == 0) {

            UsuarioModel comum = new UsuarioModel();
            comum.setNome("testeComum");
            comum.setCargo(cargoRepository.findByNome("comum"));
            comum.setEmail("comum@gmail.com");
            comum.setPassword("123456");
            usuarioRepository.save(comum);

            UsuarioModel gestor = new UsuarioModel();
            gestor.setNome("testeGestor");
            gestor.setEmail("gestor@gmail.com");
            gestor.setPassword("123456");
            gestor.setCargo(cargoRepository.findByNome("gestor"));
            usuarioRepository.save(gestor);
        }
    }
}
