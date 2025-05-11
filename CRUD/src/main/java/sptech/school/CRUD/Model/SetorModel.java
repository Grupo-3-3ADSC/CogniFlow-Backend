package sptech.school.CRUD.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SetorModel {

        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

     private String nomeSetor;

     @JoinColumn(name = "estoque_id")
    @ManyToOne
    private Integer estoque;

    @JoinColumn(name = "materia_prima_id")
    @OneToMany
    private Integer materiaPrima;




}
