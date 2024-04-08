package ir.segroup.unipoll.ws.service.impl;

import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.config.exception.constant.ExceptionMessages;
import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.shared.utils.CommentUtil;
import ir.segroup.unipoll.ws.model.entity.CommentEntity;
import ir.segroup.unipoll.ws.model.request.CommentRequest;
import ir.segroup.unipoll.ws.model.response.CommentResponse;
import ir.segroup.unipoll.ws.repository.CommentRepository;
import ir.segroup.unipoll.ws.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ir.segroup.unipoll.config.exception.constant.ExceptionMessages.DATABASE_IO_EXCEPTION;
import static java.util.stream.Collectors.toList;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final CommentUtil util;

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public CommentServiceImpl(CommentRepository commentRepository, CommentUtil util) {
        this.commentRepository = commentRepository;
        this.util = util;
    }

    @Override
    public ResponseEntity<BaseApiResponse> createComment(CommentRequest commentRequest) {
        CommentEntity commentEntity = util.convert(commentRequest);
        CommentEntity savedEntity;
        try {
            savedEntity = commentRepository.save(commentEntity);
        }catch (Exception e){
            logger.log(Level.WARNING, e.getMessage());
            throw new SystemServiceException(DATABASE_IO_EXCEPTION.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return util.createResponse(util.convert(savedEntity),HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<BaseApiResponse> getAllComments() {
        List<CommentResponse> commentResponse = commentRepository.findAll().stream()
                .map(util::convert)
                .toList();
        return util.createResponse(commentResponse,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseApiResponse> getAComment(String publicId) {
        Optional<CommentEntity> commentEntity = commentRepository.findByPublicId(publicId);
        if (commentEntity.isEmpty())
            throw new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(),HttpStatus.NOT_FOUND);
        CommentResponse commentResponse = util.convert(commentEntity.get());
        return util.createResponse(commentResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseApiResponse> deleteComment(String publicId) {
        Optional<CommentEntity> commentEntity = commentRepository.findByPublicId(publicId);
        if (commentEntity.isEmpty())
            throw new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(),HttpStatus.NOT_FOUND);
        CommentResponse commentResponse = util.convert(commentEntity.get());

        try {
            commentRepository.delete(commentEntity.get());
        }catch (Exception exception) {
            logger.log(Level.WARNING, exception.getMessage());
            throw new SystemServiceException(DATABASE_IO_EXCEPTION.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return util.createResponse(commentResponse, HttpStatus.OK);
    }


}
