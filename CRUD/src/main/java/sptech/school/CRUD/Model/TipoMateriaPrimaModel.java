package sptech.school.CRUD.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class TipoMateriaPrimaModel {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        private String TipoMaterial;
        private String largura;
        private String tamanho;
        private String espessura;

        @ManyToOne
        @JoinColumn(name = "setor_id")
        private SetorModel setor;
}
