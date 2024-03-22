package ir.segroup.unipoll.config.exception;

import ir.segroup.unipoll.shared.model.BaseApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class AppExceptionHandler {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @ExceptionHandler(value = {SystemServiceException.class})
    public ResponseEntity<BaseApiResponse> handleRelayServiceException(SystemServiceException systemServiceException) {
        logger.log(Level.WARNING, "SystemServiceException had just happened, {0}", systemServiceException.getMessage());
        BaseApiResponse response = BaseApiResponse.builder()
                .action(false)
                .message(systemServiceException.getMessage())
                .date(new Date())
                .result(new ArrayList<>())
                .build();
        return new ResponseEntity<>(response, systemServiceException.getHttpStatus());
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<BaseApiResponse> handleException(Exception exception) {
        logger.log(Level.WARNING, "Exception had just happened, {0}", exception.getMessage());
        BaseApiResponse response = BaseApiResponse.builder()
                .action(false)
                .message(exception.getMessage())
                .date(new Date())
                .result(new ArrayList<>())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

