package ir.segroup.unipoll.ws.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.ws.model.request.ContactRequest;
import ir.segroup.unipoll.ws.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact")
@Tag(name = "contact",description = "contact with us")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    @Operation(summary = "Contact with admin")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Create comment to contact admin successfully",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "System Default Exception (SDE) , or when database IO exception occurred",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            )
    })
    public ResponseEntity<BaseApiResponse> createComment(@RequestBody ContactRequest contactRequest) {
        return contactService.createComment(contactRequest);
    }

    @GetMapping
    @Operation(summary = "Get all comments from users to contact admin")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return all stored comments successfully",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            )
    })
    public ResponseEntity<BaseApiResponse> getAllComment() {
        return contactService.getAllComments();
    }

    @GetMapping("/{publicId}")
    @Operation(summary = "Get a desired comment")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "404",
                    description = "Could not find a comment with this publicId",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a desired comment successfully",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            )
    })
    public ResponseEntity<BaseApiResponse> getAComment(@PathVariable String publicId) {
        return contactService.getAComment(publicId);
    }

    @DeleteMapping("/{publicId}")
    @Operation(summary = "Delete a desired comment")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "404",
                    description = "Could not find a comment with this publicId",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "System Default Exception (SDE) , or when database IO exception occurred",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "200",
                    description = "Delete a desired comment successfully",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            )
    })
    public ResponseEntity<BaseApiResponse> deleteAComment(@PathVariable String publicId) {
        return contactService.deleteComment(publicId);
    }
}
