package ir.segroup.unipoll.shared.utils;

import ir.segroup.unipoll.ws.model.entity.InstructorCourseEntity;
import ir.segroup.unipoll.ws.model.entity.RateEntity;
import ir.segroup.unipoll.ws.model.entity.StudentEntity;
import ir.segroup.unipoll.ws.model.request.RateRequest;
import ir.segroup.unipoll.ws.model.response.ARateResponse;
import ir.segroup.unipoll.ws.model.response.RateResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RateUtil extends Util{

    private final InstructorCourseUtil instructorCourseUtil;

    public RateUtil(InstructorCourseUtil instructorCourseUtil) {
        this.instructorCourseUtil = instructorCourseUtil;
    }

    public RateResponse convert(List<RateEntity> rateEntityList, double rate) {
        return RateResponse.builder()
                .rate(rate)
                .averageRate(instructorCourseUtil.calculateRate(rateEntityList))
                .build();
    }

    public RateEntity convert(StudentEntity studentEntity, InstructorCourseEntity instructorCourseEntity, RateRequest rateRequest) {
        return RateEntity.builder()
                .number(rateRequest.getRate())
                .instructorCourseEntity(instructorCourseEntity)
                .studentEntity(studentEntity)
                .build();
    }

    public ARateResponse convert(RateEntity rateEntity){
        return ARateResponse.builder()
                .rate(rateEntity.getNumber())
                .build();
    }

    public RateEntity update(RateEntity existRate, RateRequest rateRequest){
        existRate.setNumber(rateRequest.getRate());
        return existRate;
    }
}
