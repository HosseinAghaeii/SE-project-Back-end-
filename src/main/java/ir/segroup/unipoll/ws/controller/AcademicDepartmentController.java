package ir.segroup.unipoll.ws.controller;

import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.ws.service.AcademicDepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/academic-department")
public class AcademicDepartmentController {
    private final AcademicDepartmentService academicDepartmentService;

    public AcademicDepartmentController(AcademicDepartmentService academicDepartmentService) {
        this.academicDepartmentService = academicDepartmentService;
    }

    @GetMapping
    public ResponseEntity<BaseApiResponse> getAllAcademicDepartment() {
        return academicDepartmentService.getAllDepartments();
    }

    @GetMapping("/{publicId}/manger-and-assistant")
    public ResponseEntity<BaseApiResponse> getManagerAndAssistantAcademicDepartment(@PathVariable String publicId) {
        return academicDepartmentService.getManagerAndAssistant(publicId);
    }

    @GetMapping("/{publicId}/instructors")
    public ResponseEntity<BaseApiResponse> getInstructorsAcademicDepartment(@PathVariable String publicId) {
        return academicDepartmentService.getInstructors(publicId);
    }

    @GetMapping("/{publicId}/courses")
    public ResponseEntity<BaseApiResponse> getCoursesAcademicDepartment(@PathVariable String publicId) {
        return academicDepartmentService.getCourses(publicId);
    }
}
