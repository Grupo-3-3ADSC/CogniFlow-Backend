package sptech.school.CRUD.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class RequisicaoConflitanteException extends RuntimeException{
    public RequisicaoConflitanteException(String message) {
        super(message);
    }
}
