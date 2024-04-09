package ir.segroup.unipoll.ws.service.impl;

import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.shared.utils.InstructorCourseUtil;
import ir.segroup.unipoll.ws.model.entity.InstructorCourseEntity;
import ir.segroup.unipoll.ws.model.response.InstructorCourseResponse;
import ir.segroup.unipoll.ws.repository.InstructorCourseRepository;
import ir.segroup.unipoll.ws.service.InstructorCourseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ir.segroup.unipoll.ws.repository.SpecificationImpl.InstructorCourseSpecificationImpl.fromCourseName;

@Service
public class InstructorCourseServiceImpl implements InstructorCourseService {
    private final InstructorCourseRepository instructorCourseRepository;

    private final InstructorCourseUtil instructorCourseUtil;

    public InstructorCourseServiceImpl(InstructorCourseRepository instructorCourseRepository, InstructorCourseUtil instructorCourseUtil) {
        this.instructorCourseRepository = instructorCourseRepository;
        this.instructorCourseUtil = instructorCourseUtil;
    }

    @Override
    public ResponseEntity<BaseApiResponse> findInstructorCourse(String filteredName) {
        Specification<InstructorCourseEntity> filters = Specification.where(StringUtils.isBlank(filteredName) ? null : fromCourseName(filteredName));
        List<InstructorCourseResponse> responses = instructorCourseRepository.findAll(filters)
                .stream()
                .map(instructorCourseUtil::convert)
                .toList();
        return instructorCourseUtil.createResponse(responses, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseApiResponse> getTenTopInstructorCourses() {
        HashMap<String,Double> rateMap = new HashMap<>();
        instructorCourseRepository.findAll()
                .forEach(course -> rateMap.put(course.getPublicId(),
                        instructorCourseUtil.calculateRate(course.getRateEntities())));
        List<String> tenTopPublicIdList = rateMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(10)
                .map(Map.Entry::getKey)
                .toList();
        List<InstructorCourseResponse> responses = tenTopPublicIdList.stream()
                .map(publicId -> {
                    Optional<InstructorCourseEntity> instructorCourseEntity = instructorCourseRepository.findByPublicId(publicId);
                    return instructorCourseUtil.convert(instructorCourseEntity.get());
                })
                .toList();
        return instructorCourseUtil.createResponse(responses, HttpStatus.OK);
    }
}
