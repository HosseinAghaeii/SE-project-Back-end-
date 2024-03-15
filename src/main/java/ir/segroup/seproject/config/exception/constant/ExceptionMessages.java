package ir.segroup.seproject.config.exception.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessages {

    CHECK_SECURITY("Security problem"),
    RECORD_ALREADY_EXISTS("Record already exists"),
    DATABASE_IO_EXCEPTION("Could not read/write into database")
    ;

    private final String message;
}
