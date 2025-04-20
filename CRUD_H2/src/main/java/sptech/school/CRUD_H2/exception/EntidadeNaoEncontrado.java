package sptech.school.CRUD_H2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class EntidadeNaoEncontrado extends RuntimeException {
    public EntidadeNaoEncontrado(String message) {
        super(message);
    }
}
