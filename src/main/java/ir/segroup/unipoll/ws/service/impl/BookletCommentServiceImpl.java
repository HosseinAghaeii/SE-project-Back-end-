package ir.segroup.unipoll.ws.service.impl;

import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.shared.utils.CommentUtil;
import ir.segroup.unipoll.ws.model.entity.BookletCommentEntity;
import ir.segroup.unipoll.ws.model.request.BookletCommentRequest;
import ir.segroup.unipoll.ws.model.response.BookletCommentResponse;
import ir.segroup.unipoll.ws.model.response.ICCommentResponse;
import ir.segroup.unipoll.ws.repository.BookletCommentRepository;
import ir.segroup.unipoll.ws.service.BookletCommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BookletCommentServiceImpl implements BookletCommentService {
    private final CommentUtil util;
    private final BookletCommentRepository bookletCommentRepository;

    public BookletCommentServiceImpl(CommentUtil util, BookletCommentRepository bookletCommentRepository) {
        this.util = util;
        this.bookletCommentRepository = bookletCommentRepository;
    }

    @Override
    public ResponseEntity<BaseApiResponse> createComment(BookletCommentRequest request, String token) {
        String writerUsername = util.getUsernameFromToken(token);
        BookletCommentEntity commentEntity = util.convert(request,writerUsername);
        BookletCommentEntity savedComment;
        try {
            savedComment = bookletCommentRepository.save(commentEntity);
        }catch (Exception e){
            throw new SystemServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        BookletCommentResponse response = util.convert(savedComment);
        return util.createResponse(response,HttpStatus.OK);
    }
}
