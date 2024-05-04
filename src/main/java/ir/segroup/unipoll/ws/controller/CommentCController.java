package ir.segroup.unipoll.ws.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.ws.model.request.CommentCRequest;
import ir.segroup.unipoll.ws.service.CommentCService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment-c")
@Tag(name = "Instructor course comment controller")
public class CommentCController {

    private final CommentCService commentCService;

    public CommentCController(CommentCService commentCService) {
        this.commentCService = commentCService;
    }

    @PostMapping
    @Operation(summary = "Create comment for instructor curse")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "401",
                    description = "problem in jwt header. valid users: admin,student,instructor",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "when ic public id or term public id not found",
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
    public ResponseEntity<BaseApiResponse> createComment(@RequestBody CommentCRequest commentCRequest, HttpServletRequest request){
        String token = request.getHeader("Authorization");
        return commentCService.createComment(commentCRequest,token);
    }
}