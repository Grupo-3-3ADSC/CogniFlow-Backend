package sptech.school.CRUD.Initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import sptech.school.CRUD.Model.*;
import sptech.school.CRUD.Repository.*;

@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

//    @Autowired
//    private TipoMaterialPrimaRepository materialPrimaRepository;

    @Autowired
    private EstoqueRepository estoqueRepository;

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


        if (estoqueRepository.count() == 0) {
            EstoqueModel model1 = new EstoqueModel();

            model1.setTipoMaterial("SAE 1020");
            model1.setQuantidadeMinima(10);
            model1.setQuantidadeMaxima(100000);

            EstoqueModel model2 = new EstoqueModel();
            model2.setTipoMaterial("SAE 1045");
            model2.setQuantidadeMinima(10);
            model2.setQuantidadeMaxima(100000);

            EstoqueModel model3 = new EstoqueModel();
            model3.setTipoMaterial("HARDOX 450");
            model3.setQuantidadeMinima(10);
            model3.setQuantidadeMaxima(100000);

            estoqueRepository.save(model1);
            estoqueRepository.save(model2);
            estoqueRepository.save(model3);
        }

    }



    }

