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

    @Autowired
    private TransferenciaRepository transferenciaRepository;


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

//        if (transferenciaRepository.count() == 0) {
//            TransferenciaModel model1 = new TransferenciaModel();
//
//            model1.setTipoMaterial("SAE 1020");
//            model1.setQuantidadeTransferida(50);
//            model1.setSetor("C1");
//            model1.setUltimaMovimentacao(LocalDateTime.now());
//
//            TransferenciaModel model2 = new TransferenciaModel();
//            model2.setTipoMaterial("SAE 1045");
//            model2.setQuantidadeTransferida(100);
//            model2.setSetor("C2");
//            model2.setUltimaMovimentacao(LocalDateTime.now());
//
//            TransferenciaModel model3 = new TransferenciaModel();
//            model3.setTipoMaterial("HARDOX 450");
//            model3.setQuantidadeTransferida(100);
//            model3.setSetor("C3");
//            model3.setUltimaMovimentacao(LocalDateTime.now());

//            EstoqueModel model2 = new EstoqueModel();
//            model2.setTipoMaterial("SAE 1045");
//            model2.setQuantidadeMinima(400);
//            model2.setQuantidadeMaxima(10000);
//            model2.setQuantidadeAtual(200);
//            model2.setUltimaMovimentacao(LocalDateTime.now());
//            model2.setExterno(8);
//            model2.setInterno(3);
//
//            EstoqueModel model3 = new EstoqueModel();
//            model3.setTipoMaterial("HARDOX 450");
//            model3.setQuantidadeMinima(150);
//            model3.setQuantidadeMaxima(10000);
//            model3.setQuantidadeAtual(7500);
//            model3.setUltimaMovimentacao(LocalDateTime.now());
//            model3.setExterno(2);
//            model3.setInterno(7);

//            estoqueRepository.save(model1);
//            estoqueRepository.save(model2);
//            estoqueRepository.save(model3);

//            transferenciaRepository.save(model1);
//            transferenciaRepository.save(model2);
//            transferenciaRepository.save(model3);
        }
    }






