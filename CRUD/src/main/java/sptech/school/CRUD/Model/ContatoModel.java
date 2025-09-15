package sptech.school.CRUD.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ContatoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String telefone;
    private String email;
    private String responsavel;
    private String cargo;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private FornecedorModel fornecedor;

}
