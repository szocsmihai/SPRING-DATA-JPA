package ro.vehicle_registry.exception.business.owner;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CnpAlreadyExistsAdvice {

    @ExceptionHandler(CnpAlreadyExistsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    String cnpAlreadyExistsHandler(CnpAlreadyExistsException ex) {
        return ex.getMessage();
    }
}
