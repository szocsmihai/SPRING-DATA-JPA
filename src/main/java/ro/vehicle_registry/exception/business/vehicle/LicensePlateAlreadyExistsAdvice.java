package ro.vehicle_registry.exception.business.vehicle;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class LicensePlateAlreadyExistsAdvice {

    @ExceptionHandler(LicensePlateAlreadyExistsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    String licensePlateAlreadyExistsHandler(LicensePlateAlreadyExistsException ex) {
        return ex.getMessage();
    }
}
