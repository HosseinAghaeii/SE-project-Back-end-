package ir.segroup.unipoll.ws.controller;

import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.ws.service.InstructorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instructor")
public class InstructorController {
    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping("/filter/{filteredName}")
    public ResponseEntity<BaseApiResponse> filterInstructor(@RequestParam(value = "searchQuery") String filteredName) {
        return instructorService.findInstructor(filteredName);
    }

    @GetMapping("/{publicId}")
    public ResponseEntity<BaseApiResponse> getInstructor(@PathVariable String publicId) {
        return instructorService.getInstructor(publicId);
    }

    @GetMapping("/{publicId}/courses")
    public ResponseEntity<BaseApiResponse> getInstructorCourses(@PathVariable String publicId) {
        return instructorService.getInstructorCourses(publicId);
    }

}
