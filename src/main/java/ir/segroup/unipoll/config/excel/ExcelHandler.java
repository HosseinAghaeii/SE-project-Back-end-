package ir.segroup.unipoll.config.excel;

import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.config.exception.constant.ExceptionMessages;
import ir.segroup.unipoll.shared.utils.Validator;
import ir.segroup.unipoll.ws.model.entity.*;
import ir.segroup.unipoll.ws.repository.AcademicDepartmentRepository;
import ir.segroup.unipoll.ws.repository.CollegeRepository;
import ir.segroup.unipoll.ws.repository.CourseRepository;
import ir.segroup.unipoll.ws.repository.InstructorRepository;
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
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ExcelHandler {

    private static final String USER = "users";
    private static final String COURSE = "courses";
    private static final String IC = "IC";
    private static final String COLLEGE = "colleges";
    private static final String ACADEMIC_DEPT = "academicDepartment";
    private static final String DEPT_COURSE = "dept_course";
    private static final String DEPT_INSTRUCTOR = "dept_instructor";


    private static final String NAME = "name";
    private static final String FIRSTNAME = "firstname";
    private static final String LASTNAME = "lastname";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String DESCRIPTION = "description";

    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final CollegeRepository collegeRepository;
    private final AcademicDepartmentRepository academicDepartmentRepository;
    private final Validator validator;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public ExcelHandler(CourseRepository courseRepository, InstructorRepository instructorRepository, CollegeRepository collegeRepository, AcademicDepartmentRepository academicDepartmentRepository, Validator validator, PasswordEncoder passwordEncoder) {
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
        this.collegeRepository = collegeRepository;
        this.academicDepartmentRepository = academicDepartmentRepository;
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
                UserEntity userEntity = buildOneUser(cellsInRow, ids);

                userEntities.add(userEntity);
            }
            workbook.close();
            return userEntities;
        } catch (IOException e) {
            throw new SystemServiceException(ExceptionMessages.COULD_NOT_WORK_WITH_EXCEL.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private UserEntity buildOneUser(Iterator<Cell> cellsInRow, List<Long> ids) {
        Cell currentCell = cellsInRow.next();
        validator.checkRole(currentCell);
        String role = currentCell.getStringCellValue();

        return switch (role) {
            case "ADMIN" -> fillAdminFields(cellsInRow, role, ids);
            case "INSTRUCTOR" -> fillInstructorFields(cellsInRow, role, ids);
            case "STUDENT" -> fillStudentFields(cellsInRow, role, ids);
            default -> null;
        };
    }

    private UserEntity fillAdminFields(Iterator<Cell> cellsInRow, String role, List<Long> ids) {
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

    private UserEntity fillInstructorFields(Iterator<Cell> cellsInRow, String role, List<Long> ids) {
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
                case 6: {
                    validator.checkStringFormat(currentCell, USER, "phd");
                    userEntity.setPhd(currentCell.getStringCellValue());
                }
                break;
                case 7: {
                    validator.checkStringFormat(currentCell, USER, "academicRank");
                    userEntity.setAcademicRank(currentCell.getStringCellValue());
                }
                break;
                case 8: {
                    validator.checkStringFormat(currentCell, USER, "phoneNumber");
                    userEntity.setPhoneNumber(currentCell.getStringCellValue());
                }
                break;
                case 9: {
                    validator.checkStringFormat(currentCell, USER, "email");
                    userEntity.setEmail(currentCell.getStringCellValue());
                }
                break;
                case 10: {
                    validator.checkStringFormat(currentCell, USER, "website link");
                    userEntity.setWebsiteLink(currentCell.getStringCellValue());
                }
                break;
                case 11: {
                    validator.checkStringFormat(currentCell, USER, "profile photo");
                    userEntity.setProfilePhoto(currentCell.getStringCellValue());
                }
                break;
                default:
                    break;
            }
            cellIdx++;
        }
        return userEntity;
    }

    private UserEntity fillStudentFields(Iterator<Cell> cellsInRow, String role, List<Long> ids) {
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
                case 6: {
                    validator.checkStringFormat(currentCell, USER, "major");
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

    public List<CourseEntity> excelToCourseEntity(InputStream inputStream) {
        List<Long> ids = new ArrayList<>();
        try {
            Workbook workbook = new XSSFWorkbook(inputStream);

            Sheet sheet = workbook.getSheet(COURSE);
            Iterator<Row> rows = sheet.iterator();

            List<CourseEntity> courseEntities = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                //skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                CourseEntity courseEntity = new CourseEntity();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0: {
                            validator.checkId(currentCell, ids, COURSE);
                            courseEntity.setId((long) currentCell.getNumericCellValue());
                            ids.add((long) currentCell.getNumericCellValue());
                        }
                        break;
                        case 1: {
                            validator.checkStringFormat(currentCell, COURSE, NAME);
                            courseEntity.setName(currentCell.getStringCellValue());
                        }
                        break;
                        case 2: {
                            validator.checkNumericFormat(currentCell, COURSE, "unit");
                            courseEntity.setUnit((int) currentCell.getNumericCellValue());
                        }
                        break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                courseEntities.add(courseEntity);
            }
            workbook.close();
            return courseEntities;
        } catch (IOException e) {
            throw new SystemServiceException(ExceptionMessages.COULD_NOT_WORK_WITH_EXCEL.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<InstructorCourseEntity> excelToInstructorCourseEntity(InputStream inputStream) {
        List<Long> ids = new ArrayList<>();
        try {
            Workbook workbook = new XSSFWorkbook(inputStream);

            Sheet sheet = workbook.getSheet(IC);
            Iterator<Row> rows = sheet.iterator();

            List<InstructorCourseEntity> instructorCourseEntities = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                //skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                InstructorCourseEntity instructorCourseEntity = new InstructorCourseEntity();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0: {
                            validator.checkId(currentCell, ids, IC);
                            instructorCourseEntity.setId((long) currentCell.getNumericCellValue());
                            ids.add((long) currentCell.getNumericCellValue());
                        }
                        break;
                        case 1: {
                            validator.checkStringFormat(currentCell, IC, "description");
                            instructorCourseEntity.setDescription(currentCell.getStringCellValue());
                        }
                        break;
                        case 2: {
                            validator.checkNumericFormat(currentCell, IC, "course id");
                            long courseId = (long) currentCell.getNumericCellValue();
                            courseRepository.findById(courseId).map(courseEntity -> {
                                        instructorCourseEntity.setCourseEntity(courseEntity);
                                        return instructorCourseEntity;
                                    }
                            ).orElseThrow(() -> {
                                logger.log(Level.OFF, "IC sheet:Invalid course id");
                                return new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND);
                            });

                        }
                        break;
                        case 3: {
                            validator.checkNumericFormat(currentCell, IC, "instructor id");
                            long instructorId = (long) currentCell.getNumericCellValue();
                            instructorRepository.findById(instructorId).map(instructorEntity -> {
                                instructorCourseEntity.setInstructorEntity(instructorEntity);
                                return instructorCourseEntity;
                            }).orElseThrow(() -> {
                                logger.log(Level.OFF, "IC sheet:Invalid instructor id");
                                return new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND);
                            });
                        }
                        break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                instructorCourseEntities.add(instructorCourseEntity);
            }
            workbook.close();
            return instructorCourseEntities;
        } catch (IOException e) {
            throw new SystemServiceException(ExceptionMessages.COULD_NOT_WORK_WITH_EXCEL.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<CollegeEntity> excelToCollegeEntity(InputStream inputStream) {
        List<Long> ids = new ArrayList<>();
        try {
            Workbook workbook = new XSSFWorkbook(inputStream);

            Sheet sheet = workbook.getSheet(COLLEGE);
            Iterator<Row> rows = sheet.iterator();

            List<CollegeEntity> collegeEntities = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                //skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                CollegeEntity collegeEntity = new CollegeEntity();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0: {
                            validator.checkId(currentCell, ids, COURSE);
                            collegeEntity.setId((long) currentCell.getNumericCellValue());
                            ids.add((long) currentCell.getNumericCellValue());
                        }
                        break;
                        case 1: {
                            validator.checkStringFormat(currentCell, COURSE, NAME);
                            collegeEntity.setName(currentCell.getStringCellValue());
                        }
                        break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                collegeEntities.add(collegeEntity);
            }
            workbook.close();
            return collegeEntities;
        } catch (IOException e) {
            throw new SystemServiceException(ExceptionMessages.COULD_NOT_WORK_WITH_EXCEL.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<AcademicDepartmentEntity> excelToAcademicDeptEntity(InputStream inputStream) {
        List<Long> ids = new ArrayList<>();
        try {
            Workbook workbook = new XSSFWorkbook(inputStream);

            Sheet sheet = workbook.getSheet(ACADEMIC_DEPT);
            Iterator<Row> rows = sheet.iterator();

            List<AcademicDepartmentEntity> academicDepartmentEntities = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                //skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                AcademicDepartmentEntity academicDepartmentEntity = new AcademicDepartmentEntity();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0: {
                            validator.checkId(currentCell, ids, ACADEMIC_DEPT);
                            academicDepartmentEntity.setId((long) currentCell.getNumericCellValue());
                            ids.add((long) currentCell.getNumericCellValue());
                        }
                        break;
                        case 1: {
                            validator.checkStringFormat(currentCell, ACADEMIC_DEPT, NAME);
                            academicDepartmentEntity.setName(currentCell.getStringCellValue());

                        }
                        break;
                        case 2: {
                            validator.checkStringFormat(currentCell, ACADEMIC_DEPT, DESCRIPTION);
                            academicDepartmentEntity.setDescription(currentCell.getStringCellValue());
                        }
                        break;
                        case 3: {
                            validator.checkNumericFormat(currentCell, ACADEMIC_DEPT, "college id");
                            long collegeId = (long) currentCell.getNumericCellValue();
                            collegeRepository.findById(collegeId).map(collegeEntity -> {
                                academicDepartmentEntity.setCollegeEntity(collegeEntity);
                                return academicDepartmentEntity;
                            }).orElseThrow(() -> {
                                logger.log(Level.OFF, "AcademicDept sheet:Invalid college id");
                                return new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND);
                            });
                        }
                        break;
                        case 4: {
                            validator.checkNumericFormat(currentCell, ACADEMIC_DEPT, "assistant id");
                            long assistantId = (long) currentCell.getNumericCellValue();
                            instructorRepository.findById(assistantId).map(instructorEntity -> {
                                academicDepartmentEntity.setAssistant(instructorEntity);
                                return academicDepartmentEntity;
                            }).orElseThrow(() -> {
                                logger.log(Level.OFF, "AcademicDept sheet:Invalid assistant id");
                                return new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND);
                            });
                        }
                        break;
                        case 5: {
                            validator.checkNumericFormat(currentCell, ACADEMIC_DEPT, "manager id");
                            long managerId = (long) currentCell.getNumericCellValue();
                            instructorRepository.findById(managerId).map(instructorEntity -> {
                                academicDepartmentEntity.setManager(instructorEntity);
                                return academicDepartmentEntity;
                            }).orElseThrow(() -> {
                                logger.log(Level.OFF, "AcademicDept sheet:Invalid manager id");
                                return new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND);
                            });
                        }
                        break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                academicDepartmentEntities.add(academicDepartmentEntity);
            }
            workbook.close();
            return academicDepartmentEntities;
        } catch (IOException e) {
            throw new SystemServiceException(ExceptionMessages.COULD_NOT_WORK_WITH_EXCEL.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void excelToDeptCourseRelation(InputStream inputStream) {
        try {
            Workbook workbook = new XSSFWorkbook(inputStream);

            Sheet sheet = workbook.getSheet(DEPT_COURSE);
            Iterator<Row> rows = sheet.iterator();


            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                //skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                AcademicDepartmentEntity academicDepartmentEntity = null;
                CourseEntity courseEntity = null;

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0: {
                            validator.checkNumericFormat(currentCell, DEPT_COURSE, "academic_dept_id");
                            long academicDeptId = (long) currentCell.getNumericCellValue();
                            academicDepartmentEntity = academicDepartmentRepository.findById(academicDeptId)
                                    .orElseThrow(() -> {
                                        logger.log(Level.OFF, "Dept_course sheet:Invalid academic dept id");
                                        return new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND);
                                    });
                        }
                        break;
                        case 1: {
                            validator.checkNumericFormat(currentCell, DEPT_COURSE, "course_id");
                            long courseId = (long) currentCell.getNumericCellValue();
                            courseEntity = courseRepository.findById(courseId)
                                    .orElseThrow(() -> {
                                        logger.log(Level.OFF, "Dept_course sheet:Invalid course id");
                                        return new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND);
                                    });
                        }
                        break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                assert academicDepartmentEntity != null;
                assert courseEntity != null;

                List<CourseEntity> existCourses = academicDepartmentEntity.getCourseEntities();
                List<String> existCourseNames = existCourses.stream().map(CourseEntity::getName).toList();

                if (!existCourseNames.contains(courseEntity.getName())) {
                    existCourses.add(courseEntity);
                    academicDepartmentEntity.setCourseEntities(existCourses);

                    List<AcademicDepartmentEntity> existDept = courseEntity.getAcademicDepartmentEntities();
                    existDept.add(academicDepartmentEntity);
                    courseEntity.setAcademicDepartmentEntities(existDept);
                }

                academicDepartmentRepository.save(academicDepartmentEntity);
                courseRepository.save(courseEntity);
            }
            workbook.close();

        } catch (IOException e) {
            throw new SystemServiceException(ExceptionMessages.COULD_NOT_WORK_WITH_EXCEL.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void excelToDeptInstructorRelation(InputStream inputStream) {
        try {
            Workbook workbook = new XSSFWorkbook(inputStream);

            Sheet sheet = workbook.getSheet(DEPT_INSTRUCTOR);
            Iterator<Row> rows = sheet.iterator();


            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                //skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                AcademicDepartmentEntity academicDepartmentEntity = null;
                InstructorEntity instructorEntity = null;

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0: {
                            validator.checkNumericFormat(currentCell, DEPT_INSTRUCTOR, "academic_dept_id");
                            long academicDeptId = (long) currentCell.getNumericCellValue();
                            academicDepartmentEntity = academicDepartmentRepository.findById(academicDeptId)
                                    .orElseThrow(() -> {
                                        logger.log(Level.OFF, "Dept_course sheet:Invalid academic dept id");
                                        return new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND);
                                    });
                        }
                        break;
                        case 1: {
                            validator.checkNumericFormat(currentCell, DEPT_INSTRUCTOR, "instructor_id");
                            long instructorId = (long) currentCell.getNumericCellValue();
                            instructorEntity = instructorRepository.findById(instructorId)
                                    .orElseThrow(() -> {
                                        logger.log(Level.OFF, "Dept_course sheet:Invalid instructor id");
                                        return new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND);
                                    });
                        }
                        break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                assert academicDepartmentEntity != null;
                assert instructorEntity != null;

                List<InstructorEntity> existInstructors = academicDepartmentEntity.getInstructorEntities();
                List<String> existInstructorUsernames = existInstructors.stream().map(InstructorEntity::getUsername).toList();

                if (!existInstructorUsernames.contains(instructorEntity.getUsername())) {
                    existInstructors.add(instructorEntity);
                    academicDepartmentEntity.setInstructorEntities(existInstructors);

                    List<AcademicDepartmentEntity> existDept = instructorEntity.getAcademicDeptEntities();
                    existDept.add(academicDepartmentEntity);
                    instructorEntity.setAcademicDeptEntities(existDept);
                }

                academicDepartmentRepository.save(academicDepartmentEntity);
                instructorRepository.save(instructorEntity);
            }
            workbook.close();

        } catch (IOException e) {
            throw new SystemServiceException(ExceptionMessages.COULD_NOT_WORK_WITH_EXCEL.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
