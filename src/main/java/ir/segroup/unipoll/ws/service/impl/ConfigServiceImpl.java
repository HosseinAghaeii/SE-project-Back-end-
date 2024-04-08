package ir.segroup.unipoll.ws.service.impl;

import ir.segroup.unipoll.config.excel.ExcelHandler;
import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.config.exception.constant.ExceptionMessages;
import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.shared.utils.Util;
import ir.segroup.unipoll.ws.model.entity.*;
import ir.segroup.unipoll.ws.repository.*;
import ir.segroup.unipoll.ws.service.ConfigService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ConfigServiceImpl  implements ConfigService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final InstructorCourseRepository instructorCourseRepository;
    private final CollegeRepository collegeRepository;
    private final AcademicDepartmentRepository academicDepartmentRepository;
    private final Util util;
    private final ExcelHandler excelHandler;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public ConfigServiceImpl(UserRepository userRepository, CourseRepository courseRepository, InstructorCourseRepository instructorCourseRepository, CollegeRepository collegeRepository, AcademicDepartmentRepository academicDepartmentRepository, Util util, ExcelHandler excelHandler) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.instructorCourseRepository = instructorCourseRepository;
        this.collegeRepository = collegeRepository;
        this.academicDepartmentRepository = academicDepartmentRepository;
        this.util = util;
        this.excelHandler = excelHandler;
    }

    @Override
    public ResponseEntity<BaseApiResponse> init(MultipartFile file)  {
        if (excelHandler.hasExcelFormat(file)) {
            throw new SystemServiceException(ExceptionMessages.NO_EXCEL_FORMAT.getMessage(), HttpStatus.BAD_REQUEST);
        }
        initUsers(file);

        initCourses(file);
        initIC(file);

        initColleges(file);
        initAcademicDept(file);
        initRelations(file);
        return util.createResponse(new ArrayList<>(), HttpStatus.OK);
    }

    private void initUsers(MultipartFile file){
        List<UserEntity> newUsers;
        try {
            newUsers = excelHandler.excelToUserEntity(file.getInputStream());
        }catch (IOException e){
            logger.log(Level.OFF,e.getMessage());
            throw new SystemServiceException(ExceptionMessages.INPUT_STREAM_ERROR.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        newUsers.forEach(userEntity -> userEntity.setPublicId(UUID.randomUUID().toString())); // set publicId for new user
        List<String> newUsernames = new ArrayList<>(newUsers.stream().map(UserEntity::getUsername).toList());

        List<UserEntity> existedUsers = userRepository.findAll();
        List<String> existedUsernames = existedUsers.stream().map(UserEntity::getUsername).toList();

        newUsernames.removeAll(existedUsernames.stream().filter(newUsernames::contains).toList());
        newUsers.forEach(userEntity -> {
            if (newUsernames.contains(userEntity.getUsername())){
                userRepository.save(userEntity);
            }
        });
    }

    private void initCourses(MultipartFile file){
        List<CourseEntity> newCourses;
        try {
            newCourses = excelHandler.excelToCourseEntity(file.getInputStream());
        }catch (IOException e){
            logger.log(Level.OFF,e.getMessage());
            throw new SystemServiceException(ExceptionMessages.INPUT_STREAM_ERROR.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        newCourses.forEach(courseEntity -> courseEntity.setPublicId(UUID.randomUUID().toString())); // set publicId for new courses

        List<String> newCourseNames = new ArrayList<>(newCourses.stream().map(CourseEntity::getName).toList());

        List<CourseEntity> existedCourse = courseRepository.findAll();
        List<String> existedCourseName = existedCourse.stream().map(CourseEntity::getName).toList();

        newCourseNames.removeAll(existedCourseName.stream().filter(newCourseNames::contains).toList());
        newCourses.forEach(courseEntity -> {
            if (newCourseNames.contains(courseEntity.getName())){
                courseRepository.save(courseEntity);
            }
        });
    }

    private void initIC(MultipartFile file){
        List<InstructorCourseEntity> newIC;
        try {
            newIC = excelHandler.excelToInstructorCourseEntity(file.getInputStream());
        }catch (IOException e){
            logger.log(Level.OFF,e.getMessage());
            throw new SystemServiceException(ExceptionMessages.INPUT_STREAM_ERROR.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        newIC.forEach(instructorCourseEntity -> instructorCourseEntity.setPublicId(UUID.randomUUID().toString())); // set publicId for new IC

        List<Long> newICId = new ArrayList<>(newIC.stream().map(InstructorCourseEntity::getId).toList());

        List<InstructorCourseEntity> existedIC = instructorCourseRepository.findAll();
        List<Long> existedICId = existedIC.stream().map(InstructorCourseEntity::getId).toList();

        newICId.removeAll(existedICId.stream().filter(newICId::contains).toList());
        newIC.forEach(iCEntity -> {
            if (newICId.contains(iCEntity.getId())){
                instructorCourseRepository.save(iCEntity);
            }
        });
    }

    private void initColleges(MultipartFile file){
        List<CollegeEntity> newColleges;
        try {
            newColleges = excelHandler.excelToCollegeEntity(file.getInputStream());
        }catch (IOException e){
            logger.log(Level.OFF,e.getMessage());
            throw new SystemServiceException(ExceptionMessages.INPUT_STREAM_ERROR.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        newColleges.forEach(courseEntity -> courseEntity.setPublicId(UUID.randomUUID().toString())); // set publicId for new colleges

        List<String> newCollegesNames = new ArrayList<>(newColleges.stream().map(CollegeEntity::getName).toList());

        List<CollegeEntity> existedColleges = collegeRepository.findAll();
        List<String> existedCollegesName = existedColleges.stream().map(CollegeEntity::getName).toList();

        newCollegesNames.removeAll(existedCollegesName.stream().filter(newCollegesNames::contains).toList());
        newColleges.forEach(collegeEntity -> {
            if (newCollegesNames.contains(collegeEntity.getName())){
                collegeRepository.save(collegeEntity);
            }
        });
    }

    private void initAcademicDept(MultipartFile file){
        List<AcademicDepartmentEntity> newAcademicDepts;
        try {
            newAcademicDepts = excelHandler.excelToAcademicDeptEntity(file.getInputStream());
        }catch (IOException e){
            logger.log(Level.OFF,e.getMessage());
            throw new SystemServiceException(ExceptionMessages.INPUT_STREAM_ERROR.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        newAcademicDepts.forEach(academicDepartmentEntity -> academicDepartmentEntity.setPublicId(UUID.randomUUID().toString())); // set publicId for new academic dept

        List<String> newAcademicDeptNames = new ArrayList<>(newAcademicDepts.stream().map(AcademicDepartmentEntity::getName).toList());

        List<AcademicDepartmentEntity> existedAcademicDepts = academicDepartmentRepository.findAll();
        List<String> existedAcademicDeptNames = existedAcademicDepts.stream().map(AcademicDepartmentEntity::getName).toList();

        newAcademicDeptNames.removeAll(existedAcademicDeptNames.stream().filter(newAcademicDeptNames::contains).toList());
        newAcademicDepts.forEach(academicDepartmentEntity -> {
            if (newAcademicDeptNames.contains(academicDepartmentEntity.getName())){
                academicDepartmentRepository.save(academicDepartmentEntity);
            }
        });
    }

    private void initRelations(MultipartFile file){
        try {
             excelHandler.excelToDeptCourseRelation(file.getInputStream());
             excelHandler.excelToDeptInstructorRelation(file.getInputStream());
        }catch (IOException e){
            logger.log(Level.OFF,e.getMessage());
            throw new SystemServiceException(ExceptionMessages.INPUT_STREAM_ERROR.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
