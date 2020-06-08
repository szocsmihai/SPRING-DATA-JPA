package ro.vehicle_registry.exception.business.address;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AddressAlreadyExistsAdvice {

    @ExceptionHandler(AddressAlreadyExistsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    String addressAlreadyExistsHandler(AddressAlreadyExistsException ex) {
        return ex.getMessage();
    }
}
