package ro.vehicle_registry.exception.business.contact_card;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PhoneNoAlreadyExistsAdvice {

    @ExceptionHandler(PhoneNoAlreadyExistsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    String phoneNoAlreadyExistsHandler(PhoneNoAlreadyExistsException ex) {
        return ex.getMessage();
    }
}
