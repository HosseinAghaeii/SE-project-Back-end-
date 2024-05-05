package ir.segroup.unipoll.ws.service.impl;

import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.config.exception.constant.ExceptionMessages;
import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.shared.utils.CommentCUtil;
import ir.segroup.unipoll.ws.model.entity.CommentCEntity;
import ir.segroup.unipoll.ws.model.request.CommentCRequest;
import ir.segroup.unipoll.ws.model.response.CommentCResponse;
import ir.segroup.unipoll.ws.repository.CommentCRepository;
import ir.segroup.unipoll.ws.service.CommentCService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CommentCServiceImpl implements CommentCService {

    private final CommentCUtil util;
    private final CommentCRepository commentCRepository;

    public CommentCServiceImpl(CommentCUtil util, CommentCRepository commentCRepository) {
        this.util = util;
        this.commentCRepository = commentCRepository;
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
        CommentCResponse response = util.convert(savedComment, request.isUnknown());
        return util.createResponse(response,HttpStatus.OK);
    }
}
