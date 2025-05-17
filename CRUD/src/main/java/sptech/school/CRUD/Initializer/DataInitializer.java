package sptech.school.CRUD.Initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import sptech.school.CRUD.Model.CargoModel;
import sptech.school.CRUD.Model.FornecedorModel;
import sptech.school.CRUD.Model.TipoMateriaPrimaModel;
import sptech.school.CRUD.Model.UsuarioModel;
import sptech.school.CRUD.Repository.CargoRepository;
import sptech.school.CRUD.Repository.FornecedorRepository;
import sptech.school.CRUD.Repository.TipoMaterialPrimaRepository;
import sptech.school.CRUD.Repository.UsuarioRepository;

@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private TipoMaterialPrimaRepository materialPrimaRepository;


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

            UsuarioModel john = new UsuarioModel();
            john.setNome("John Doe");
            john.setEmail("john@doe.com");
            john.setPassword("$2a$10$0/TKTGxdREbWaWjWYhwf6e9P1fPOAMMNqEnZgOG95jnSkHSfkkIrC");
            john.setCargo(cargoRepository.findByNome("gestor"));
            usuarioRepository.save(john);

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

        if(fornecedorRepository.count() == 0){
            FornecedorModel fornecedor = new FornecedorModel();
            fornecedor.setCnpj("12345678901234");
            fornecedor.setNomeFantasia("teste");
            fornecedor.setRazaoSocial("teste");

            fornecedorRepository.save(fornecedor);
        }
        if (materialPrimaRepository.count()== 0){
            TipoMateriaPrimaModel model1 = new TipoMateriaPrimaModel();
            model1.setTipoMaterial("SAE 1020");
            materialPrimaRepository.save(model1);

            TipoMateriaPrimaModel model2 = new TipoMateriaPrimaModel();
            model2.setTipoMaterial("SAE 1045");
            materialPrimaRepository.save(model2);

            TipoMateriaPrimaModel model3 = new TipoMateriaPrimaModel();
            model3.setTipoMaterial("HARDOX 450");
            materialPrimaRepository.save(model3);
        }



    }
}
