package ir.segroup.unipoll.ws.controller;

import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.ws.service.AcademicDepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.PublicKey;

@RestController
@RequestMapping("/academic-department")
public class AcademicDepartmentController {
    private final AcademicDepartmentService academicDepartmentService;

    public AcademicDepartmentController(AcademicDepartmentService academicDepartmentService) {
        this.academicDepartmentService = academicDepartmentService;
    }

    @GetMapping("/{departmentName}")
    public ResponseEntity<BaseApiResponse> getAcademicDepartment(@PathVariable String departmentName) {
        return academicDepartmentService.getDescription(departmentName);
    }

    @GetMapping("/manger-and-assistant/{departmentName}")
    public ResponseEntity<BaseApiResponse> getManagerAndAssistantAcademicDepartment(@PathVariable String departmentName) {
        return academicDepartmentService.getManagerAndAssistant(departmentName);
    }

    @GetMapping("/instructors/{departmentName}")
    public ResponseEntity<BaseApiResponse> getInstructorsAcademicDepartment(@PathVariable String departmentName) {
        return academicDepartmentService.getInstructors(departmentName);
    }

    @GetMapping("/courses/{departmentName}")
    public ResponseEntity<BaseApiResponse> getCoursesAcademicDepartment(@PathVariable String departmentName) {
        return academicDepartmentService.getCourses(departmentName);
    }
}
