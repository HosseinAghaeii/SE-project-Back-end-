package ir.segroup.unipoll.shared.utils;

import ir.segroup.unipoll.ws.model.entity.CommentEntity;
import ir.segroup.unipoll.ws.model.entity.CourseEntity;
import ir.segroup.unipoll.ws.model.request.CommentRequest;
import ir.segroup.unipoll.ws.model.request.CourseRequest;
import ir.segroup.unipoll.ws.model.response.CommentResponse;
import ir.segroup.unipoll.ws.model.response.CourseResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CommentUtil extends Util{

    private final ModelMapper modelMapper;

    public CommentUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CommentEntity convert(CommentRequest commentRequest){
        CommentEntity result = modelMapper.map(commentRequest, CommentEntity.class);
        result.setPublicId(UUID.randomUUID().toString());
        return result;
    }

    public CommentResponse convert(CommentEntity commentEntity){
        return modelMapper.map(commentEntity,CommentResponse.class);
    }
}
