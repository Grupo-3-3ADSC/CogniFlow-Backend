package sptech.school.CRUD.dto.Cep;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * DTO para representar a resposta da API ViaCEP.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnderecoViaCepDto {

    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade; // cidade
    private String uf;
    private boolean erro; // se true, o CEP não existe
}
