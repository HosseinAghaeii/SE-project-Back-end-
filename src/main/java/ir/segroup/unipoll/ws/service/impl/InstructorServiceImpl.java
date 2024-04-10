package ir.segroup.unipoll.ws.service.impl;

import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.config.exception.constant.ExceptionMessages;
import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.shared.utils.InstructorCourseUtil;
import ir.segroup.unipoll.shared.utils.InstructorUtil;
import ir.segroup.unipoll.ws.model.entity.InstructorEntity;
import ir.segroup.unipoll.ws.model.response.InstructorCourseResponse;
import ir.segroup.unipoll.ws.model.response.InstructorResponse;
import ir.segroup.unipoll.ws.repository.InstructorCourseRepository;
import ir.segroup.unipoll.ws.repository.InstructorRepository;
import ir.segroup.unipoll.ws.service.InstructorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static ir.segroup.unipoll.ws.repository.SpecificationImpl.InstructorSpecificationImpl.fromInstructorFirstname;
import static ir.segroup.unipoll.ws.repository.SpecificationImpl.InstructorSpecificationImpl.fromInstructorLastname;

@Service
public class InstructorServiceImpl implements InstructorService {
    private final InstructorRepository instructorRepository;

    private final InstructorUtil instructorUtil;

    private final InstructorCourseUtil instructorCourseUtil;

    public InstructorServiceImpl(InstructorRepository instructorRepository, InstructorCourseRepository instructorCourseRepository, InstructorUtil instructorUtil, InstructorCourseUtil instructorCourseUtil) {
        this.instructorRepository = instructorRepository;
        this.instructorUtil = instructorUtil;
        this.instructorCourseUtil = instructorCourseUtil;
    }

    @Override
    public ResponseEntity<BaseApiResponse> findInstructor(String filteredName) {
        Specification<InstructorEntity> filters = Specification.where(StringUtils.isBlank(filteredName) ? null : fromInstructorFirstname(filteredName)
                .or(fromInstructorLastname(filteredName)));
        List<InstructorResponse> responses = instructorRepository.findAll(filters)
                .stream()
                .map(instructorUtil::convert)
                .toList();
        return instructorUtil.createResponse(responses, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseApiResponse> getInstructor(String publicId) {
        Optional<InstructorEntity> instructorEntity = instructorRepository.findByPublicId(publicId);
        if (instructorEntity.isEmpty())
            throw new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(),HttpStatus.NOT_FOUND);
        InstructorResponse instructorResponse = instructorUtil.convert(instructorEntity.get());
        return instructorUtil.createResponse(instructorResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseApiResponse> getInstructorCourses(String publicId) {
        Optional<InstructorEntity> instructorEntity = instructorRepository.findByPublicId(publicId);
        if (instructorEntity.isEmpty())
            throw new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(),HttpStatus.NOT_FOUND);

        List<InstructorCourseResponse> instructorCourseResponse = instructorEntity.get().getIcEntities().stream()
                .map(instructorCourseUtil::convert)
                .toList();
        return instructorCourseUtil.createResponse(instructorCourseResponse,HttpStatus.OK);
    }
}
