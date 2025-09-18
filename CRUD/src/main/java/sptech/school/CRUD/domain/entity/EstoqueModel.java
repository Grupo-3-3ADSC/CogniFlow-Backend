package sptech.school.CRUD.domain.entity;

import jakarta.persistence.*;
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
        private Double ipi;
        private LocalDateTime ultimaMovimentacao;



}
