package ir.segroup.unipoll.ws.service;

import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.ws.model.request.CommentCRequest;
import org.springframework.http.ResponseEntity;

public interface InstructorCourseCommentService {
    ResponseEntity<BaseApiResponse> createComment(CommentCRequest request,String token);

    ResponseEntity<BaseApiResponse> getAIcComments(String icPublicId, boolean filterTopFive,String termPublicId);
}
