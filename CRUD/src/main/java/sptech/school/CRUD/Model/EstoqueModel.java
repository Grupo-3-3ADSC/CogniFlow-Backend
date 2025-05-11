package sptech.school.CRUD.Model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class EstoqueModel {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        private Integer quantidadeAtual;
        private Integer quantidadeMinima;
        private Integer quantidadeMaxima;
        private LocalDateTime ultimaMovimentacao;

        @ManyToOne
        @JoinColumn(name = "materia_prima_id")
        private TipoMateriaPrimaModel materiaPrima;


}
