package ir.segroup.unipoll.ws.service.impl;

import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.config.exception.constant.ExceptionMessages;
import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.shared.utils.CommentUtil;
import ir.segroup.unipoll.ws.model.entity.BookletCommentEntity;
import ir.segroup.unipoll.ws.model.entity.ICCommentEntity;
import ir.segroup.unipoll.ws.model.entity.TermEntity;
import ir.segroup.unipoll.ws.model.request.BookletCommentRequest;
import ir.segroup.unipoll.ws.model.response.BookletCommentResponse;
import ir.segroup.unipoll.ws.model.response.ICCommentResponse;
import ir.segroup.unipoll.ws.repository.BookletCommentRepository;
import ir.segroup.unipoll.ws.repository.BookletRepository;
import ir.segroup.unipoll.ws.service.BookletCommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class BookletCommentServiceImpl implements BookletCommentService {
    private final CommentUtil util;
    private final BookletCommentRepository bookletCommentRepository;
    private final BookletRepository bookletRepository;

    public BookletCommentServiceImpl(CommentUtil util, BookletCommentRepository bookletCommentRepository, BookletRepository bookletRepository) {
        this.util = util;
        this.bookletCommentRepository = bookletCommentRepository;
        this.bookletRepository = bookletRepository;
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

    @Override
    public ResponseEntity<BaseApiResponse> getABookletComments(String publicId, boolean filterTopFive) {
        return bookletRepository.findByPublicId(publicId)
                .map(bookletEntity -> {
                    List<BookletCommentEntity> bookletCommentEntities = bookletCommentRepository.findAll().stream()
                            .filter(entity -> entity.getBookletEntity().getPublicId().equals(publicId))
                            .sorted(Comparator.comparing(BookletCommentEntity::getCreatedDate).reversed()).toList();
                    if (filterTopFive){
                        bookletCommentEntities = bookletCommentEntities.stream().limit(5).toList();
                    }
                    List<BookletCommentResponse> responses = bookletCommentEntities.stream().map(util::convert).toList();
                    return util.createResponse(responses,HttpStatus.OK);
                })
                .orElseThrow(() ->
                        new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND));
    }
}
