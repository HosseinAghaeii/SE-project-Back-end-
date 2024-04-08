package ir.segroup.unipoll.ws.service;

import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.ws.model.request.CommentRequest;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    ResponseEntity<BaseApiResponse> createComment(CommentRequest commentRequest);
    ResponseEntity<BaseApiResponse> getAllComments();
    ResponseEntity<BaseApiResponse> getAComment(String publicId);
    ResponseEntity<BaseApiResponse> deleteComment(String publicId);
}
