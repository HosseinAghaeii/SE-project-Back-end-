package ir.segroup.unipoll.ws.service;

import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.ws.model.request.CollegeRequest;
import org.springframework.http.ResponseEntity;

public interface CollegeService {
    ResponseEntity<BaseApiResponse> createCollege(CollegeRequest collegeRequest);
}
