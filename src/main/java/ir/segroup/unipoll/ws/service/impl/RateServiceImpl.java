package ir.segroup.unipoll.ws.service.impl;

import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.config.exception.constant.ExceptionMessages;
import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.shared.utils.RateUtil;
import ir.segroup.unipoll.ws.model.entity.InstructorCourseEntity;
import ir.segroup.unipoll.ws.model.entity.RateEntity;
import ir.segroup.unipoll.ws.model.entity.StudentEntity;
import ir.segroup.unipoll.ws.model.request.RateRequest;
import ir.segroup.unipoll.ws.model.response.ARateResponse;
import ir.segroup.unipoll.ws.model.response.InstructorCourseResponse;
import ir.segroup.unipoll.ws.model.response.RateResponse;
import ir.segroup.unipoll.ws.repository.InstructorCourseRepository;
import ir.segroup.unipoll.ws.repository.RateRepository;
import ir.segroup.unipoll.ws.repository.StudentRepository;
import ir.segroup.unipoll.ws.service.RateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class RateServiceImpl implements RateService {

    private final RateRepository rateRepository;

    private final InstructorCourseRepository instructorCourseRepository;

    private final RateUtil rateUtil;

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final StudentRepository studentRepository;

    public RateServiceImpl(RateRepository rateRepository, InstructorCourseRepository instructorCourseRepository, RateUtil rateUtil, StudentRepository studentRepository) {
        this.rateRepository = rateRepository;
        this.instructorCourseRepository = instructorCourseRepository;
        this.rateUtil = rateUtil;
        this.studentRepository = studentRepository;
    }

    @Override
    public ResponseEntity<BaseApiResponse> addInstructorCourseRate(String token, String publicId, RateRequest rateRequest) {
        String username = rateUtil.getUsernameFromToken(token);
            StudentEntity studentEntity = studentRepository.findByUsername(username).orElseThrow(() -> {
                logger.log(Level.OFF, "Failed to get student details");
                return new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND);
            });
            InstructorCourseEntity instructorCourseEntity = instructorCourseRepository.findByPublicId(publicId).orElseThrow(() -> {
                logger.log(Level.OFF, "Failed to get instructor course details");
                return new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        });
        Optional<RateEntity> existRate = rateRepository.findByStudentEntityAndInstructorCourseEntity(studentEntity,instructorCourseEntity);
        RateEntity rateEntity;
        if (existRate.isPresent()) {
            rateEntity = rateUtil.update(existRate.get(), rateRequest);
        }else {
            rateEntity = rateUtil.convert(studentEntity, instructorCourseEntity, rateRequest);
        }
        RateEntity savedRateEntity;
        try {
            savedRateEntity = rateRepository.save(rateEntity);
        }catch (Exception exception) {
            logger.log(Level.OFF, exception.getMessage());
            throw new SystemServiceException(ExceptionMessages.DATABASE_IO_EXCEPTION.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<RateEntity> rateList = rateRepository.findByInstructorCourseEntity(instructorCourseEntity);
        return rateUtil.createResponse(rateUtil.convert(rateList, savedRateEntity.getNumber()),HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<BaseApiResponse> getARate(String token, String publicId) {
        String username = rateUtil.getUsernameFromToken(token);
        StudentEntity studentEntity = studentRepository.findByUsername(username).orElseThrow(() -> {
            logger.log(Level.OFF, "Failed to get student details");
            return new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        });
        InstructorCourseEntity instructorCourseEntity = instructorCourseRepository.findByPublicId(publicId).orElseThrow(() -> {
            logger.log(Level.OFF, "Failed to get instructor course details");
            return new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND);
        });
        Optional<RateEntity> rateEntity = rateRepository.findByStudentEntityAndInstructorCourseEntity(studentEntity,instructorCourseEntity);
        if (rateEntity.isEmpty())
            throw new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(),HttpStatus.NOT_FOUND);
        ARateResponse rateResponse = rateUtil.convert(rateEntity.get());
        return rateUtil.createResponse(rateResponse, HttpStatus.OK);
    }
}
