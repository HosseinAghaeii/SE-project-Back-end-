package ir.segroup.unipoll.config.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class SystemServiceException extends RuntimeException {

    private final String exception;
    private final HttpStatus httpStatus;

    public SystemServiceException(String exception, HttpStatus httpStatus) {
        super(exception);
        this.exception = exception;
        this.httpStatus = httpStatus;
    }
}
