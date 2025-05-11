package sptech.school.CRUD.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

public class TipoMateriaPrimaModel {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)

        private String TipoMaterial;
        private String largura;
        private String tamanho;
        private String espessura;







}
