package sptech.school.CRUD.Initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import sptech.school.CRUD.Model.*;
import sptech.school.CRUD.Repository.*;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Override
    public void run(String... args) throws Exception {

        if (cargoRepository.count() == 0) {

            CargoModel comum = new CargoModel();
            comum.setNome("comum");
            cargoRepository.save(comum);

            CargoModel gestor = new CargoModel();
            gestor.setNome("gestor");
            cargoRepository.save(gestor);
        }


        if (usuarioRepository.count() == 0) {

            UsuarioModel john = new UsuarioModel();
            john.setNome("John Doe");
            john.setEmail("john@doe.com");
            john.setPassword("$2a$10$0/TKTGxdREbWaWjWYhwf6e9P1fPOAMMNqEnZgOG95jnSkHSfkkIrC");
            john.setCargo(cargoRepository.findByNome("gestor"));
            usuarioRepository.save(john);

        }

        if (fornecedorRepository.count() == 0) {
            FornecedorModel fornecedor = new FornecedorModel();
            fornecedor.setCnpj("12345678901234");
            fornecedor.setNomeFantasia("teste");
            fornecedor.setRazaoSocial("teste");
            fornecedorRepository.save(fornecedor);
        }

        if (estoqueRepository.count() == 0) {
            EstoqueModel model1 = new EstoqueModel();

            model1.setTipoMaterial("SAE 1020");
            model1.setQuantidadeMinima(300);
            model1.setQuantidadeMaxima(10000);
            model1.setQuantidadeAtual(1800);
            model1.setUltimaMovimentacao(LocalDateTime.now());
            model1.setInterno(2);
            model1.setExterno(5);

            EstoqueModel model2 = new EstoqueModel();
            model2.setTipoMaterial("SAE 1045");
            model2.setQuantidadeMinima(400);
            model2.setQuantidadeMaxima(10000);
            model2.setQuantidadeAtual(200);
            model2.setUltimaMovimentacao(LocalDateTime.now());
            model2.setExterno(8);
            model2.setInterno(3);

            EstoqueModel model3 = new EstoqueModel();
            model3.setTipoMaterial("HARDOX 450");
            model3.setQuantidadeMinima(150);
            model3.setQuantidadeMaxima(10000);
            model3.setQuantidadeAtual(7500);
            model3.setUltimaMovimentacao(LocalDateTime.now());
            model3.setExterno(2);
            model3.setInterno(7);

            estoqueRepository.save(model1);
            estoqueRepository.save(model2);
            estoqueRepository.save(model3);
        }
    }

    }




