package ir.segroup.unipoll.ws.service;

import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.ws.model.request.RateRequest;
import org.springframework.http.ResponseEntity;

public interface RateService {
    ResponseEntity<BaseApiResponse> addInstructorCourseRate(String token, String publicId, RateRequest rateRequest);

    ResponseEntity<BaseApiResponse> getARate(String token, String publicId);
}
