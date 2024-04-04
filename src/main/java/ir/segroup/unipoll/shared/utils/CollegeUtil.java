package ir.segroup.unipoll.shared.utils;

import ir.segroup.unipoll.ws.model.entity.CollegeEntity;
import ir.segroup.unipoll.ws.model.entity.CourseEntity;
import ir.segroup.unipoll.ws.model.request.CollegeRequest;
import ir.segroup.unipoll.ws.model.request.CourseRequest;
import ir.segroup.unipoll.ws.model.response.CollegeResponse;
import ir.segroup.unipoll.ws.model.response.CourseResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CollegeUtil extends Util{
    private final ModelMapper modelMapper;

    public CollegeUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CollegeEntity convert(CollegeRequest collegeRequest){
        CollegeEntity result = modelMapper.map(collegeRequest, CollegeEntity.class);
        result.setPublicId(UUID.randomUUID().toString());
        return result;
    }

    public CollegeResponse convert(CollegeEntity collegeEntity){
        return modelMapper.map(collegeEntity, CollegeResponse.class);
    }

}
