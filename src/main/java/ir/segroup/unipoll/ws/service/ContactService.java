package ir.segroup.unipoll.ws.service;

import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.ws.model.request.ContactRequest;
import org.springframework.http.ResponseEntity;

public interface ContactService {
    ResponseEntity<BaseApiResponse> createComment(ContactRequest contactRequest);
    ResponseEntity<BaseApiResponse> getAllComments();
    ResponseEntity<BaseApiResponse> getAComment(String publicId);
    ResponseEntity<BaseApiResponse> deleteComment(String publicId);
}
