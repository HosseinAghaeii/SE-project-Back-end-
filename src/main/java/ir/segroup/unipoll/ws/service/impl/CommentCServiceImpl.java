package ir.segroup.unipoll.ws.service.impl;

import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.config.exception.constant.ExceptionMessages;
import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.shared.utils.CommentCUtil;
import ir.segroup.unipoll.ws.model.entity.CommentCEntity;
import ir.segroup.unipoll.ws.model.entity.InstructorCourseEntity;
import ir.segroup.unipoll.ws.model.request.CommentCRequest;
import ir.segroup.unipoll.ws.model.response.CommentCResponse;
import ir.segroup.unipoll.ws.repository.CommentCRepository;
import ir.segroup.unipoll.ws.repository.InstructorCourseRepository;
import ir.segroup.unipoll.ws.service.CommentCService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class CommentCServiceImpl implements CommentCService {

    private final CommentCUtil util;
    private final CommentCRepository commentCRepository;
    private final InstructorCourseRepository icRepository;

    public CommentCServiceImpl(CommentCUtil util, CommentCRepository commentCRepository, InstructorCourseRepository icRepository) {
        this.util = util;
        this.commentCRepository = commentCRepository;
        this.icRepository = icRepository;
    }

    @Override
    public ResponseEntity<BaseApiResponse> createComment(CommentCRequest request, String token) {
        String writerUsername = util.getUsernameFromToken(token);
        CommentCEntity commentCEntity = util.convert(request,writerUsername);
        CommentCEntity savedComment;
        try {
            savedComment = commentCRepository.save(commentCEntity);
        }catch (Exception e){
            throw new SystemServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        CommentCResponse response = util.convert(savedComment);
        return util.createResponse(response,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseApiResponse> getAIcComments(String icPublicId, boolean filterTopFive) {
         return icRepository.findByPublicId(icPublicId)
                .map(instructorCourseEntity -> {
                    List<CommentCEntity> commentCEntities = commentCRepository.findAll().stream()
                            .filter(entity -> entity.getIcEntity().getPublicId().equals(icPublicId))
                            .sorted(Comparator.comparing(CommentCEntity::getCreatedDate).reversed()).toList();
                    if (filterTopFive){
                        commentCEntities = commentCEntities.stream().limit(5).toList();
                    }
                    List<CommentCResponse> responses = commentCEntities.stream().map(util::convert).toList();
                    return util.createResponse(responses,HttpStatus.OK);
                })
                .orElseThrow(() ->
                new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND));
    }
}
