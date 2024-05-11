package ir.segroup.unipoll.ws.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.ws.model.request.RateRequest;
import ir.segroup.unipoll.ws.service.RateService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rate")
@Tag(name = "Rate controller")
public class RateController {

    private final RateService rateService;

    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    @PostMapping("/{publicId}")
    @Operation(summary = "Add rate to instructor course")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "successfully add rate",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Username or instructor course public id not found",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "This api authenticate for Student,instructor and admin",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "System Default Exception (SDE), or when database IO exception occurred",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            )
    })
    public ResponseEntity<BaseApiResponse> addInstructorCourseRate(HttpServletRequest request,
                                                                   @PathVariable String publicId,
                                                                   @RequestBody RateRequest rateRequest) {
        String token = request.getHeader("Authorization");
        return rateService.addInstructorCourseRate(token, publicId, rateRequest);
    }

    @GetMapping("/{publicId}")
    @Operation(summary = "Add rate to instructor course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404",
                    description = "Could not find a rate",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}),
            @ApiResponse(responseCode = "200",
                    description = "Return a rate successfully",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")})})
    public ResponseEntity<BaseApiResponse> getARate(HttpServletRequest request, @PathVariable String publicId) {
        String token = request.getHeader("Authorization");
        return rateService.getARate(token, publicId);
    }
}
