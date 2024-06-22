package ir.segroup.unipoll.ws.service;

import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.ws.model.request.BookletCommentRequest;
import org.springframework.http.ResponseEntity;

public interface BookletCommentService {
    ResponseEntity<BaseApiResponse> createComment(BookletCommentRequest bookletCommentRequest, String token);

    ResponseEntity<BaseApiResponse> getABookletComments(String publicId, boolean filterTopFive);
}
