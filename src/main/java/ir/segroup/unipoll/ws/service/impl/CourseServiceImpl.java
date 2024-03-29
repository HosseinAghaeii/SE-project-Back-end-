package ir.segroup.unipoll.ws.service.impl;

import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.config.exception.constant.ExceptionMessages;
import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.shared.utils.CourseUtil;
import ir.segroup.unipoll.ws.model.entity.CourseEntity;
import ir.segroup.unipoll.ws.model.request.CourseRequest;
import ir.segroup.unipoll.ws.repository.CourseRepository;
import ir.segroup.unipoll.ws.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseUtil util;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public CourseServiceImpl(CourseRepository courseRepository, CourseUtil util) {
        this.courseRepository = courseRepository;
        this.util = util;
    }

    @Override
    public ResponseEntity<BaseApiResponse> createCourse(CourseRequest courseRequest) {
        Optional<CourseEntity> existedCourse = courseRepository.findByName(courseRequest.getName());
        if (existedCourse.isPresent()){
            throw new SystemServiceException(ExceptionMessages.RECORD_ALREADY_EXISTS.getMessage(), HttpStatus.CONFLICT);
        }
        CourseEntity courseEntity = util.convert(courseRequest);
        CourseEntity savedEntity ;
        try {
            savedEntity = courseRepository.save(courseEntity);
        }catch (Exception e){
            logger.log(Level.WARNING, e.getMessage());
            throw new SystemServiceException(ExceptionMessages.DATABASE_IO_EXCEPTION.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return util.createResponse(util.convert(savedEntity),HttpStatus.CREATED);
    }
}
