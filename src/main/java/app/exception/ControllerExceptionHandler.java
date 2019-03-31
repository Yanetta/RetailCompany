package app.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import app.dto.ErrorMessage;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger LOG = LogManager.getLogger(ControllerExceptionHandler.class);
    private static final Logger LOGMAIL = LogManager.getLogger("error-logger");

    private Map<String, String> validationCodeDescription = new HashMap<>();

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    ErrorMessage unexpected(Exception e) {
        LOG.error("Unexpected exception {}", e.getMessage());
        return new ErrorMessage(e.getMessage());
    }

    @ExceptionHandler(value = {DeleteException.class, UpdateException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public @ResponseBody
    ErrorMessage entityExistingProblem(Exception e) {
        LOG.warn("Unprocesable entity {}", e.getMessage());
        return new ErrorMessage(e.getMessage());
    }
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorMessage validationProblem(MethodArgumentNotValidException e) {
        LOG.warn("Request validation problem {}", e.getMessage());
        FieldError fe = e.getBindingResult().getFieldError();
        return new ErrorMessage(validationCodeDescription.get(fe.getDefaultMessage()));
    }
    @PostConstruct
    private void intValidationCodeDescription() {
        validationCodeDescription.put("1", "Customer must not be null");
        validationCodeDescription.put("2", "credit limit must not be more than 9999");
        validationCodeDescription.put("3", "custnum must not be less than 999");
        validationCodeDescription.put("4", "length should not be less than two symbols");
        validationCodeDescription.put("5", "name should not contain digit symbols, should not be less than 2 symbols and more than 15 symbols");
    }
}