package sptech.school.CRUD.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class OrdemDeCompraModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String ie;
    private LocalDateTime prazoEntrega;
    private Double valorKg;
    private Double valorPeca;
    private String rastreamento;
    private Double valorUnitario;
    private Double ipi;
    private LocalDateTime condicaoPagamento;

    @ManyToOne
    @JoinColumn(name = "materia_prima_id")
    private Integer materiaPrima;
    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private Integer fornecedor;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioModel usuario;






}
