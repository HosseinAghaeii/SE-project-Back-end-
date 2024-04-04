package ir.segroup.unipoll.config.exception.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessages {

    CHECK_SECURITY("Security problem"),
    RECORD_ALREADY_EXISTS("Record already exists"),
    DATABASE_IO_EXCEPTION("Could not read/write into database"),
    AUTHENTICATION_FAILED("Authentication failed"),
    INVALID_ROLE("role field has invalid value.Valid roles are : ADMIN , STUDENT , TEACHER"),
    NO_RECORD_FOUND("Record is not found"),
    FILE_EXCEPTION("Exception is related to file"),
    EMPTY_FILE("File is empty"),
    COULD_NOT_CREAT_DIRECTORY("Could not create the directory where the uploaded files will be stored");

    private final String message;
}
