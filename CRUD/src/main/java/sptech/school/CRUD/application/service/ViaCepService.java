package sptech.school.CRUD.application.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sptech.school.CRUD.interfaces.dto.Cep.EnderecoViaCepDto;

@Service
public class ViaCepService {

    public EnderecoViaCepDto buscarEnderecoPorCep(String cep) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://viacep.com.br/ws/" + cep + "/json";

        EnderecoViaCepDto endereco = restTemplate.getForObject(url, EnderecoViaCepDto.class);

        if (endereco == null || endereco.getCep() == null || endereco.isErro()) {
            throw new IllegalArgumentException("CEP inválido ou inexistente.");
        }

        return endereco;
    }

}
