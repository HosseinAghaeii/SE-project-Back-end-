package ir.segroup.unipoll.ws.service.impl;

import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.shared.utils.InstructorUtil;
import ir.segroup.unipoll.ws.model.entity.InstructorEntity;
import ir.segroup.unipoll.ws.model.response.InstructorResponse;
import ir.segroup.unipoll.ws.repository.InstructorRepository;
import ir.segroup.unipoll.ws.service.InstructorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static ir.segroup.unipoll.ws.repository.SpecificationImpl.InstructorSpecificationImpl.fromInstructorFirstname;
import static ir.segroup.unipoll.ws.repository.SpecificationImpl.InstructorSpecificationImpl.fromInstructorLastname;

@Service
public class InstructorServiceImpl implements InstructorService {
    private final InstructorRepository instructorRepository;
    private final InstructorUtil instructorUtil;

    public InstructorServiceImpl(InstructorRepository instructorRepository, InstructorUtil instructorUtil) {
        this.instructorRepository = instructorRepository;
        this.instructorUtil = instructorUtil;
    }

    @Override
    public ResponseEntity<BaseApiResponse> findInstructor(String filteredName) {
        Specification<InstructorEntity> filters = Specification.where(StringUtils.isBlank(filteredName) ? null : fromInstructorFirstname(filteredName)
                .and(fromInstructorLastname(filteredName)));
        List<InstructorResponse> responses = instructorRepository.findAll(filters)
                .stream()
                .map(instructorUtil::convert)
                .toList();
        return instructorUtil.createResponse(responses, HttpStatus.OK);
    }
}
