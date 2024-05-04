package ir.segroup.unipoll.ws.service;

import ir.segroup.unipoll.shared.model.BaseApiResponse;
import org.springframework.http.ResponseEntity;

public interface InstructorCourseService {
    ResponseEntity<BaseApiResponse> findInstructorCourse(String filteredName);

    ResponseEntity<BaseApiResponse> getTenTopInstructorCourses();


    ResponseEntity<BaseApiResponse> getAInstructorCourse(String publicId);


    ResponseEntity<BaseApiResponse> editDescription(String publicId,String token,String newDescription);

}
