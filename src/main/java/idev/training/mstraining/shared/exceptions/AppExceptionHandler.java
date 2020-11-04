package idev.training.mstraining.shared.exceptions;

import java.util.Date;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import idev.training.mstraining.web.model.ErrorMessage;

@EnableWebMvc
@ControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAnyException(
            Exception ex
    ) {
        String errMessage = ex.getLocalizedMessage() == null ? ex.toString() : ex.getLocalizedMessage();
        ErrorMessage errorMessage = ErrorMessage.builder()
                                                .timespamp(new Date())
                                                .message(errMessage)
                                                .build();
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
