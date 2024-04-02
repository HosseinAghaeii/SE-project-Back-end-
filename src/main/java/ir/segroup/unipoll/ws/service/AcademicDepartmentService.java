package ir.segroup.unipoll.ws.service;

import ir.segroup.unipoll.shared.model.BaseApiResponse;
import org.springframework.http.ResponseEntity;

public interface AcademicDepartmentService {
    ResponseEntity<BaseApiResponse> getDescription (String departmentName);

    ResponseEntity<BaseApiResponse> getManagerAndAssistant(String departmentName);

    ResponseEntity<BaseApiResponse> getInstructors(String departmentName);

    ResponseEntity<BaseApiResponse> getCourses(String departmentName);
}
