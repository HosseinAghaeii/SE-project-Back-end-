package ir.segroup.unipoll.ws.service;

import ir.segroup.unipoll.shared.model.BaseApiResponse;
import org.springframework.http.ResponseEntity;

public interface TermService {
    ResponseEntity<BaseApiResponse> getAllTerms();
}
