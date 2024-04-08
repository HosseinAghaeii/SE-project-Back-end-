package ir.segroup.unipoll.ws.service;

import ir.segroup.unipoll.shared.model.BaseApiResponse;
import org.springframework.http.ResponseEntity;

public interface AcademicDepartmentService {
    ResponseEntity<BaseApiResponse> getManagerAndAssistant(String publicId);

    ResponseEntity<BaseApiResponse> getInstructors(String publicId);

    ResponseEntity<BaseApiResponse> getCourses(String publicId);

    ResponseEntity<BaseApiResponse> getAllDepartments();

    ResponseEntity<BaseApiResponse> getADepartment(String publicId);
}
