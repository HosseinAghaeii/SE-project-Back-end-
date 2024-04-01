package ir.segroup.unipoll.ws.service;

import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.ws.model.request.BookletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface BookletService  {
    ResponseEntity<byte[]> downloadBooklet(String publicId);
    ResponseEntity<BaseApiResponse> uploadBooklet(MultipartFile booklet, BookletRequest bookletRequest);
    ResponseEntity<BaseApiResponse> getTenTopBooklets();
}
