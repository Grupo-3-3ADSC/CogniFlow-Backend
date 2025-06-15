package sptech.school.CRUD.Model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@RequiredArgsConstructor
@Getter
@Setter
@Entity
public class EstoqueModel {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        private String tipoMaterial;
        private Integer quantidadeAtual;
        private Integer quantidadeMinima;
        private Integer quantidadeMaxima;
        private Integer externo;
        private Integer interno;
        private LocalDateTime ultimaMovimentacao;



}
