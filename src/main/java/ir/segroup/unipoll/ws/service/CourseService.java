package ir.segroup.unipoll.ws.service;

import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.ws.model.request.CourseRequest;
import org.springframework.http.ResponseEntity;

public interface CourseService {
    ResponseEntity<BaseApiResponse> createCourse(CourseRequest courseRequest);
}
