package ir.segroup.unipoll.shared.utils;

import ir.segroup.unipoll.ws.model.entity.InstructorCourseEntity;
import ir.segroup.unipoll.ws.model.entity.RateEntity;
import ir.segroup.unipoll.ws.model.response.InstructorCourseResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InstructorCourseUtil extends Util{

    public InstructorCourseResponse convert(InstructorCourseEntity instructorCourseEntity) {
        return InstructorCourseResponse.builder()
                .courseName(instructorCourseEntity.getCourseEntity().getName())
                .instructorCourseFirstname(instructorCourseEntity.getInstructorEntity().getFirstname())
                .instructorCourseLastname(instructorCourseEntity.getInstructorEntity().getLastname())
                .description(instructorCourseEntity.getDescription())
                .rate(calculateRate(instructorCourseEntity.getRateEntities()))
                .unit(instructorCourseEntity.getCourseEntity().getUnit())
                .build();
    }

    public double calculateRate(List<RateEntity> rateEntityList) {
        return rateEntityList.stream()
                .mapToDouble(RateEntity::getNumber)
                .average()
                .orElse(0.0);
    }
}
