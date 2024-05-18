package ir.segroup.unipoll.ws.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.ws.model.request.BookletCommentRequest;
import ir.segroup.unipoll.ws.service.BookletCommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment-b")
@Tag(name = "Booklet comment controller")
public class BookletCommentController {

    private final BookletCommentService bookletCommentService;

    public BookletCommentController(BookletCommentService bookletCommentService) {
        this.bookletCommentService = bookletCommentService;
    }

    @PostMapping()
    @Operation(summary = "Create comment for Booklet")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "401",
                    description = "problem in jwt header. valid users: admin,student,instructor",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "when booklet public id or term public id not found",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "200",
                    description = "successfully create comment",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "System Default Exception (SDE), or when database IO exception occurred",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            )
    })
    public ResponseEntity<BaseApiResponse> createComment(@RequestBody BookletCommentRequest bookletCommentRequest,
                                                         HttpServletRequest request){
        String token = request.getHeader("Authorization");
        return bookletCommentService.createComment(bookletCommentRequest,token);
    }

    @GetMapping("/{publicId}")
    @Operation(summary = "Get comments of one booklet. this methode have a query param: firstTopFive. default value = false")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "404",
                    description = "when booklet public id not found",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "200",
                    description = "successfully get list of comment",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "System Default Exception (SDE), or when database IO exception occurred",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            )
    })
    public ResponseEntity<BaseApiResponse> getAIcComments(@PathVariable String publicId,
                                                          @RequestParam(defaultValue = "false") boolean filterTopFive){
        return bookletCommentService.getABookletComments(publicId,filterTopFive);
    }
}
