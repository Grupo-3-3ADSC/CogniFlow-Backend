package sptech.school.CRUD.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class EnderecoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String cep;
    private Integer numero;
    private String complemento;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private FornecedorModel fornecedor;

}
