package sptech.school.CRUD_H2.Initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import sptech.school.CRUD_H2.Model.CargoModel;
import sptech.school.CRUD_H2.Repository.CargoRepository;

@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CargoRepository cargoRepository;

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
    }
}
