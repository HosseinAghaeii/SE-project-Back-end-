package ir.segroup.unipoll.ws.service;

import ir.segroup.unipoll.shared.model.BaseApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ConfigService {
    ResponseEntity<BaseApiResponse> init(MultipartFile file) ;
}
