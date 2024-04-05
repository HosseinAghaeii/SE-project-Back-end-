package ir.segroup.unipoll.config.excel;

import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.config.exception.constant.ExceptionMessages;
import ir.segroup.unipoll.shared.utils.Validator;
import ir.segroup.unipoll.ws.model.entity.InstructorEntity;
import ir.segroup.unipoll.ws.model.entity.StudentEntity;
import ir.segroup.unipoll.ws.model.entity.UserEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Component
public class ExcelHandler {

    private static final String USER = "users";
    private static final String FIRSTNAME = "firstname";
    private static final String LASTNAME = "lastname";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private final Validator validator;
    private final PasswordEncoder passwordEncoder;

    public ExcelHandler(Validator validator, PasswordEncoder passwordEncoder) {
        this.validator = validator;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean hasExcelFormat(MultipartFile file) {
        return Objects.requireNonNull(file.getContentType()).contains("/xlsx") ||
                Objects.requireNonNull(file.getContentType()).contains("/xls");
    }

    public List<UserEntity> excelToUserEntity(InputStream inputStream) {
        List<Long> ids = new ArrayList<>();
        try {
            Workbook workbook = new XSSFWorkbook(inputStream);

            Sheet sheet = workbook.getSheet(USER);
            Iterator<Row> rows = sheet.iterator();
            List<UserEntity> userEntities = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                //skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();
                UserEntity userEntity = buildOneUser(cellsInRow,ids);

                userEntities.add(userEntity);
            }
            workbook.close();
            return userEntities;
        } catch (IOException e) {
            throw new SystemServiceException(ExceptionMessages.COULD_NOT_WORK_WITH_EXCEL.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private UserEntity buildOneUser(Iterator<Cell> cellsInRow,List<Long> ids) {
        Cell currentCell = cellsInRow.next();
        validator.checkRole(currentCell);
        String role = currentCell.getStringCellValue();

        return switch (role) {
            case "ADMIN" -> fillAdminFields(cellsInRow,role,ids);
            case "INSTRUCTOR" -> fillInstructorFields(cellsInRow,role,ids);
            case "STUDENT" -> fillStudentFields(cellsInRow,role,ids);
            default -> null;
        };
    }

    private UserEntity fillAdminFields(Iterator<Cell> cellsInRow,String role,List<Long> ids) {
        UserEntity userEntity = new UserEntity();
        userEntity.setRole(role);
        int cellIdx = 1;
        while (cellsInRow.hasNext()) {

            Cell currentCell = cellsInRow.next();

            switch (cellIdx) {
                case 1: {
                    validator.checkId(currentCell, ids, USER);
                    userEntity.setId((long) currentCell.getNumericCellValue());
                    ids.add((long) currentCell.getNumericCellValue());
                }
                break;
                case 2: {
                    validator.checkStringFormat(currentCell, USER, FIRSTNAME);
                    userEntity.setFirstname(currentCell.getStringCellValue());
                }
                break;
                case 3: {
                    validator.checkStringFormat(currentCell, USER, LASTNAME);
                    userEntity.setLastname(currentCell.getStringCellValue());
                }
                break;
                case 4: {
                    validator.checkStringFormat(currentCell, USER, USERNAME);
                    userEntity.setUsername(currentCell.getStringCellValue());
                }
                break;
                case 5: {
                    validator.checkStringFormat(currentCell, USER, PASSWORD);
                    userEntity.setEncryptedPassword(passwordEncoder.encode(currentCell.getStringCellValue()));
                }
                break;
                default:
                    break;
            }
            cellIdx++;
        }
        return userEntity;
    }

    private UserEntity fillInstructorFields(Iterator<Cell> cellsInRow,String role,List<Long> ids) {
        InstructorEntity userEntity = new InstructorEntity();
        userEntity.setRole(role);
        int cellIdx = 1;
        while (cellsInRow.hasNext()) {

            Cell currentCell = cellsInRow.next();

            switch (cellIdx) {
                case 1: {
                    validator.checkId(currentCell, ids, USER);
                    userEntity.setId((long) currentCell.getNumericCellValue());
                    ids.add((long) currentCell.getNumericCellValue());
                }
                break;
                case 2: {
                    validator.checkStringFormat(currentCell, USER, FIRSTNAME);
                    userEntity.setFirstname(currentCell.getStringCellValue());
                }
                break;
                case 3: {
                    validator.checkStringFormat(currentCell, USER, LASTNAME);
                    userEntity.setLastname(currentCell.getStringCellValue());
                }
                break;
                case 4: {
                    validator.checkStringFormat(currentCell, USER, USERNAME);
                    userEntity.setUsername(currentCell.getStringCellValue());
                }
                break;
                case 5: {
                    validator.checkStringFormat(currentCell, USER, PASSWORD);
                    userEntity.setEncryptedPassword(passwordEncoder.encode(currentCell.getStringCellValue()));
                }
                break;
                case 6:{
                    validator.checkStringFormat(currentCell,USER,"phd");
                    userEntity.setPhd(currentCell.getStringCellValue());
                }
                break;
                case 7:{
                    validator.checkStringFormat(currentCell,USER,"academicRank");
                    userEntity.setAcademicRank(currentCell.getStringCellValue());
                }
                break;
                case 8:{
                    validator.checkStringFormat(currentCell,USER,"phoneNumber");
                    userEntity.setPhoneNumber(currentCell.getStringCellValue());
                }
                break;
                case 9:{
                    validator.checkStringFormat(currentCell,USER,"email");
                    userEntity.setEmail(currentCell.getStringCellValue());
                }
                break;
                case 10:{
                    validator.checkStringFormat(currentCell,USER,"website link");
                    userEntity.setWebsiteLink(currentCell.getStringCellValue());
                }
                break;
                default:
                    break;
            }
            cellIdx++;
        }
        return userEntity;
    }

    private UserEntity fillStudentFields(Iterator<Cell> cellsInRow,String role,List<Long> ids) {
        StudentEntity userEntity = new StudentEntity();
        userEntity.setRole(role);
        int cellIdx = 1;
        while (cellsInRow.hasNext()) {

            Cell currentCell = cellsInRow.next();

            switch (cellIdx) {
                case 1: {
                    validator.checkId(currentCell, ids, USER);
                    userEntity.setId((long) currentCell.getNumericCellValue());
                    ids.add((long) currentCell.getNumericCellValue());
                }
                break;
                case 2: {
                    validator.checkStringFormat(currentCell, USER, FIRSTNAME);
                    userEntity.setFirstname(currentCell.getStringCellValue());
                }
                break;
                case 3: {
                    validator.checkStringFormat(currentCell, USER, LASTNAME);
                    userEntity.setLastname(currentCell.getStringCellValue());
                }
                break;
                case 4: {
                    validator.checkStringFormat(currentCell, USER, USERNAME);
                    userEntity.setUsername(currentCell.getStringCellValue());
                }
                break;
                case 5: {
                    validator.checkStringFormat(currentCell, USER, PASSWORD);
                    userEntity.setEncryptedPassword(passwordEncoder.encode(currentCell.getStringCellValue()));
                }
                break;
                case 6:{
                    validator.checkStringFormat(currentCell,USER,"major");
                    userEntity.setMajor(currentCell.getStringCellValue());
                }
                break;
                default:
                    break;
            }
            cellIdx++;
        }
        return userEntity;
    }
}
