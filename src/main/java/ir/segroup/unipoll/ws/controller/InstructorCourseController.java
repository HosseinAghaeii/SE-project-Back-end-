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


    @PutMapping("/edit-description/{publicId}")
    @Operation(summary = "Update ic description")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "401",
                    description = "Only teachers can call this api.If another user try to call this methode app send 401 code",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "When the teacher or administrator who does not own this lesson tries to change the description",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "when ic public id not found",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "200",
                    description = "successfully edit description",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "System Default Exception (SDE), or when database IO exception occurred",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            )
    })
    public ResponseEntity<BaseApiResponse> editDescription(@PathVariable String publicId,
                                                           @RequestBody String newDescription,
                                                           HttpServletRequest request){
        String token = request.getHeader("Authorization");
        return instructorCourseService.editDescription(publicId,token,newDescription);
    }
}
