package ir.segroup.unipoll.ws.service.impl;

import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.config.exception.constant.ExceptionMessages;
import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.shared.utils.CommentUtil;
import ir.segroup.unipoll.ws.model.entity.InstructorCourseCommentEntity;
import ir.segroup.unipoll.ws.model.entity.TermEntity;
import ir.segroup.unipoll.ws.model.request.CommentCRequest;
import ir.segroup.unipoll.ws.model.response.CommentResponse;
import ir.segroup.unipoll.ws.repository.ICCommentRepository;
import ir.segroup.unipoll.ws.repository.InstructorCourseRepository;
import ir.segroup.unipoll.ws.repository.TermRepository;
import ir.segroup.unipoll.ws.service.InstructorCourseCommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class InstructorCourseCommentServiceImpl implements InstructorCourseCommentService {

    private final CommentUtil util;
    private final ICCommentRepository commentCRepository;
    private final InstructorCourseRepository icRepository;
    private final TermRepository termRepository;

    public InstructorCourseCommentServiceImpl(CommentUtil util, ICCommentRepository commentCRepository, InstructorCourseRepository icRepository, TermRepository termRepository) {
        this.util = util;
        this.commentCRepository = commentCRepository;
        this.icRepository = icRepository;
        this.termRepository = termRepository;
    }

    @Override
    public ResponseEntity<BaseApiResponse> createComment(CommentCRequest request, String token) {
        String writerUsername = util.getUsernameFromToken(token);
        InstructorCourseCommentEntity commentCEntity = util.convert(request,writerUsername);
        InstructorCourseCommentEntity savedComment;
        try {
            savedComment = commentCRepository.save(commentCEntity);
        }catch (Exception e){
            throw new SystemServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        CommentResponse response = util.convert(savedComment);
        return util.createResponse(response,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseApiResponse> getAIcComments(String icPublicId, boolean filterTopFive,String termPublicId) {
         return icRepository.findByPublicId(icPublicId)
                .map(instructorCourseEntity -> {
                    List<InstructorCourseCommentEntity> commentCEntities = commentCRepository.findAll().stream()
                            .filter(entity -> entity.getIcEntity().getPublicId().equals(icPublicId))
                            .sorted(Comparator.comparing(InstructorCourseCommentEntity::getCreatedDate).reversed()).toList();
                    if (!termPublicId.equals("null")){
                        TermEntity termEntity = termRepository.findByPublicId(termPublicId).orElseThrow(() ->
                                new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND));
                        commentCEntities = commentCEntities.stream()
                                .filter(entity -> entity.getTermEntity().getPublicId().equals(termEntity.getPublicId())).toList();
                    }
                    if (filterTopFive){
                        commentCEntities = commentCEntities.stream().limit(5).toList();
                    }
                    List<CommentResponse> responses = commentCEntities.stream().map(util::convert).toList();
                    return util.createResponse(responses,HttpStatus.OK);
                })
                .orElseThrow(() ->
                new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND));
    }
}
