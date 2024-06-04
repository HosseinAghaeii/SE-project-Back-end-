package ir.segroup.unipoll.config.exception.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessages {

    CHECK_SECURITY("Security problem"),
    BAD_DATA_FORMAT("Missing required format. Please check documentation for required fields and their format"),
    RECORD_ALREADY_EXISTS("Record already exists"),
    DATABASE_IO_EXCEPTION("Could not read/write into database"),
    AUTHENTICATION_FAILED("Authentication failed"),
    INVALID_ROLE("role field has invalid value.Valid roles are : ADMIN , STUDENT , TEACHER"),
    ID_MUST_BE_DIGIT("The id column values must be numeric.In the excel file,a cell has violated this rule"),
    ID_MUST_BE_UNIQUE("The id column values must be unique.In the excel file,a cell has violated this rule"),
    COULD_NOT_WORK_WITH_EXCEL("Can`t open and close port"),
    NO_EXCEL_FORMAT("Uploaded file is not excel file"),
    INPUT_STREAM_ERROR("There is an error about work with input stream"),
    NO_RECORD_FOUND("Record is not found"),
    FILE_EXCEPTION("Exception is related to file"),
    EMPTY_FILE("File is empty"),
    COULD_NOT_CREAT_DIRECTORY("Could not create the directory where the uploaded files will be stored"),
    DUPLICATED_LIKE("This user has already liked booklet"),
    FORBIDDEN_EDIT_IC_DESCRIPTION_REQUEST("Only the teacher of this course can edit its description"),
    FORBIDDEN_DELETE_BOOKLET_REQUEST("Only the uploader of this booklet can delete it"),
    UNLIKE_REQUEST_WITHOUT_LIKE("The user has not liked this booklet before"),
    DISSAVE_REQUEST_WITHOUT_SAVE("The user has not saved this booklet before"),
    ;

    private final String message;
}
