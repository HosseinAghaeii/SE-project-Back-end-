package ir.segroup.unipoll.ws.service;

import ir.segroup.unipoll.shared.model.BaseApiResponse;
import org.springframework.http.ResponseEntity;

public interface InstructorService {
    ResponseEntity<BaseApiResponse> findInstructor(String filteredName);

    ResponseEntity<BaseApiResponse> getInstructor(String publicId);

    ResponseEntity<BaseApiResponse> getInstructorCourses(String publicId);
}
