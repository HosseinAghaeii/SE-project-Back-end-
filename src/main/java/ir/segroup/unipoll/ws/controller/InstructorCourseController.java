package ir.segroup.unipoll.ws.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.ws.service.InstructorCourseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instructor-course")
@Tag(name = "Instructor Course controller")
public class InstructorCourseController {
    private final InstructorCourseService instructorCourseService;

    public InstructorCourseController(InstructorCourseService instructorCourseService) {
        this.instructorCourseService = instructorCourseService;
    }

        @GetMapping("/filter")
    public ResponseEntity<BaseApiResponse> filterInstructorCourse(@RequestParam(value = "searchQuery") String filteredName) {
        return instructorCourseService.findInstructorCourse(filteredName);
    }


    @GetMapping
    @Operation(summary = "Get top ten Instructor course")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a top ten instructor course",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "System Default Exception (SDE), or when database IO exception occurred",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            )
    })
    public ResponseEntity<BaseApiResponse> getTenTopInstructorCourses() {
        return instructorCourseService.getTenTopInstructorCourses();
    }


    @GetMapping("booklets/{publicId}")
      public ResponseEntity<BaseApiResponse> getAInstructorCourse(@PathVariable String publicId) {
        return instructorCourseService.getAInstructorCourse(publicId);
    }


    
    public ResponseEntity<BaseApiResponse> getInstructorCourseBooklets(HttpServletRequest request, @PathVariable String publicId) {
        String token = request.getHeader("Authorization");
        return instructorCourseService.getInstructorCourseBooklets(token,publicId);


    @PutMapping("/edit-description/{publicId}")            
    public ResponseEntity<BaseApiResponse> editDescription(@PathVariable String publicId,
                                                           @RequestBody String newDescription,
                                                           HttpServletRequest request){
        String token = request.getHeader("Authorization");
        return instructorCourseService.editDescription(publicId,token,newDescription);

    }

}
