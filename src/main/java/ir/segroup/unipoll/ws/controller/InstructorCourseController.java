package ir.segroup.unipoll.ws.controller;

import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.ws.service.InstructorCourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/instructor-course")
public class InstructorCourseController {
    private final InstructorCourseService instructorCourseService;

    public InstructorCourseController(InstructorCourseService instructorCourseService) {
        this.instructorCourseService = instructorCourseService;
    }

    @GetMapping("/filter/{filteredName}")
    public ResponseEntity<BaseApiResponse> filterInstructorCourse(@RequestParam(value = "searchQuery") String filteredName) {
        return instructorCourseService.findInstructorCourse(filteredName);
    }
}
