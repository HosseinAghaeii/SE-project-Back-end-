package ir.segroup.unipoll.shared.utils;

import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.config.exception.constant.ExceptionMessages;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class Validator {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public void checkId(Cell cell, List<Long> ids, String entityName) {
        if (!cell.getCellType().equals(CellType.NUMERIC)) {
            String logMsg1 = entityName + ": " + "Not numeric value in id column was seen.";
            logger.log(Level.OFF, logMsg1);
            throw new SystemServiceException(ExceptionMessages.ID_MUST_BE_DIGIT.getMessage(), HttpStatus.CONFLICT);
        }
        long id = (long) cell.getNumericCellValue();
        if (ids.contains(id)) {
            String logMsg2 = entityName + ": " + "Not unique value in id column was seen: " + id;
            logger.log(Level.OFF, logMsg2);
            throw new SystemServiceException(ExceptionMessages.ID_MUST_BE_UNIQUE.getMessage(), HttpStatus.CONFLICT);
        }
    }

    public void checkStringFormat(Cell cell, String sheetName,String fieldName) {
        if (!cell.getCellType().equals(CellType.STRING)) {
            String logMsg = sheetName + "(" + fieldName + "): " + "This field must be String";
            logger.log(Level.OFF, logMsg);
            throw new SystemServiceException(ExceptionMessages.BAD_DATA_FORMAT.getMessage(), HttpStatus.CONFLICT);
        }
    }

    public void checkRole(Cell cell){
        checkStringFormat(cell,"users","role");
        List<String> validRoles = List.of("ADMIN", "STUDENT", "INSTRUCTOR");
        if (!validRoles.contains(cell.getStringCellValue())) {
            logger.log(Level.OFF,"Invalid role value. valid value are:ADMIN,INSTRUCTOR,STUDENT");
            throw new SystemServiceException(ExceptionMessages.INVALID_ROLE.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
