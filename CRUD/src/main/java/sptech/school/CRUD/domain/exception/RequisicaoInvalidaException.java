package sptech.school.CRUD.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class RequisicaoInvalidaException extends RuntimeException {

    public RequisicaoInvalidaException(String message) {
        super(message);
    }
}




