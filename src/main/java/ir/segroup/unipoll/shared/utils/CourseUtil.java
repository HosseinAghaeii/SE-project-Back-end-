package ir.segroup.unipoll.shared.utils;

import ir.segroup.unipoll.ws.model.entity.CourseEntity;
import ir.segroup.unipoll.ws.model.request.CourseRequest;
import ir.segroup.unipoll.ws.model.response.CourseResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CourseUtil extends Util{

    private final ModelMapper modelMapper;

    public CourseUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CourseEntity convert(CourseRequest courseRequest){
        CourseEntity result = modelMapper.map(courseRequest, CourseEntity.class);
        result.setPublicId(UUID.randomUUID().toString());
        return result;
    }

    public CourseResponse convert(CourseEntity courseEntity){
        return modelMapper.map(courseEntity,CourseResponse.class);
    }


}
