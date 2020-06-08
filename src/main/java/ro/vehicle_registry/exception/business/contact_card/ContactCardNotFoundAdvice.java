package ro.vehicle_registry.exception.business.contact_card;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ContactCardNotFoundAdvice {


    @ExceptionHandler(ContactCardNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String contactInfoNotFoundHandler(ContactCardNotFoundException ex) {
        return ex.getMessage();
    }
}
