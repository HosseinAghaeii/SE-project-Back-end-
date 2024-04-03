package ir.segroup.unipoll.ws.controller;

import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.ws.model.request.CommentRequest;
import ir.segroup.unipoll.ws.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<BaseApiResponse> createComment(@RequestBody CommentRequest commentRequest) {
        return commentService.createComment(commentRequest);
    }

    @GetMapping
    public ResponseEntity<BaseApiResponse> getAllComment() {
        return commentService.getAllComments();
    }

    @GetMapping("/{publicId}")
    public ResponseEntity<BaseApiResponse> getAComment(@PathVariable String publicId) {
        return commentService.getAComment(publicId);
    }

    @DeleteMapping("/{publicId}")
    public ResponseEntity<BaseApiResponse> deleteAComment(@PathVariable String publicId) {
        return commentService.deleteComment(publicId);
    }
}
