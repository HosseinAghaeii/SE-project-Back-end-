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
            @ApiResponse(responseCode = "200",
            description = "Return a top ten instructor course",
            content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}),
            @ApiResponse(responseCode = "500",
                    description = "System Default Exception (SDE), or when database IO exception occurred",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")})})
    public ResponseEntity<BaseApiResponse> getTenTopInstructorCourses() {
        return instructorCourseService.getTenTopInstructorCourses();
    }


    @GetMapping("/{publicId}")
    @Operation(summary = "Get a desired course of instructor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404",
                    description = "Could not find a course with this publicId",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}),
            @ApiResponse(responseCode = "200",
                    description = "Return a desired course successfully",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")})})
    public ResponseEntity<BaseApiResponse> getAInstructorCourse(@PathVariable String publicId) {
        return instructorCourseService.getAInstructorCourse(publicId);
    }


    @GetMapping("booklets/{publicId}")
    @Operation(summary = "Get all booklets of a course of instructor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404",
            description = "Could not find a course of instructor with this publicId",
            content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}),
            @ApiResponse(responseCode = "200",
                    description = "Return all booklets of a course of instructor successfully",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")})})
    public ResponseEntity<BaseApiResponse> getInstructorCourseBooklets(HttpServletRequest request, @PathVariable String publicId) {
        String token = request.getHeader("Authorization");
        return instructorCourseService.getInstructorCourseBooklets(token, publicId);
    }


    @PutMapping("/edit-description/{publicId}")
    @Operation(summary = "Update ic description")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401",
            description = "Only teachers can call this api.If another user try to call this methode app send 401 code",
            content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}),
            @ApiResponse(responseCode = "403",
                    description = "When the teacher or administrator who does not own this lesson tries to change the description",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}),
            @ApiResponse(responseCode = "404",
                    description = "when ic public id not found",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}),
            @ApiResponse(responseCode = "200",
                    description = "successfully edit description",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}),
            @ApiResponse(responseCode = "500",
                    description = "System Default Exception (SDE), or when database IO exception occurred",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")})})
    public ResponseEntity<BaseApiResponse> editDescription(@PathVariable String publicId,
                                                           @RequestBody String newDescription,
                                                           HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return instructorCourseService.editDescription(publicId, token, newDescription);

    }

    @GetMapping("/enable-to-edit/{publicId}")
    @Operation(summary = "Specifies whether the user is allowed to change the course description or not")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401",
                    description = "Only teachers can call this api.If another user try to call this methode or jwt is wrong app send 401 code",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}),
            @ApiResponse(responseCode = "403",
                    description = "When the teacher or administrator who does not own this lesson tries to change the description",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}),
            @ApiResponse(responseCode = "404",
                    description = "when ic public id not found",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}),
            @ApiResponse(responseCode = "200",
                    description = "successfully return old description",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}),
            @ApiResponse(responseCode = "500",
                    description = "System Default Exception (SDE), or when database IO exception occurred",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")})})
    public ResponseEntity<BaseApiResponse> isEnableToEdit(@PathVariable String publicId,HttpServletRequest request){
        String token = request.getHeader("Authorization");
        return instructorCourseService.isEnableToEdit(publicId,token);
    }

}
