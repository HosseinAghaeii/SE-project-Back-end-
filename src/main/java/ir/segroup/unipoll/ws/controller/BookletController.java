package ir.segroup.unipoll.ws.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.ws.model.request.BookletRequest;
import ir.segroup.unipoll.ws.service.BookletService;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/booklet")
@MultipartConfig
@Tag(name = "booklet",description = "booklet endpoints")
public class BookletController {
    private final BookletService bookletService;

    public BookletController(BookletService bookletService) {
        this.bookletService = bookletService;
    }

    @GetMapping("/file/{publicId}")
    @Operation(summary = "Download a booklet from database")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "404",
                    description = "When could not found the booklet with this publicId",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "200",
                    description = "Return stored Booklet Successfully",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "System Default Exception (SDE) , or when database IO exception occurred",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            )
    })
    public ResponseEntity<byte[]> downloadBooklet(@PathVariable String publicId){
        return bookletService.downloadBooklet(publicId);
    }

    @PostMapping("/file")
    @Operation(summary = "Upload a new booklet into database")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "404",
                    description = "Username not found",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Error in get data -array of bytes- from MultipartFile",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "201",
                    description = "Return a stored booklet with a path",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error in getting instructor course public id or term",
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
    public ResponseEntity<BaseApiResponse> uploadBooklet(@RequestParam("file") MultipartFile booklet,
                                                         @ModelAttribute("bookletRequest") BookletRequest bookletRequest,
                                                         HttpServletRequest request){
        String token = request.getHeader("Authorization");
        return bookletService.uploadBooklet(booklet, bookletRequest, token);
    }

    @GetMapping
    @Operation(summary = "Get top ten booklet")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Return a top ten booklet",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "System Default Exception (SDE), or when database IO exception occurred",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            )
    })
    public ResponseEntity<BaseApiResponse> getTenTopBooklets(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return bookletService.getTenTopBooklets(token);
    }


    @PostMapping("/like/{bookletPublicId}")
    @Operation(summary = "Like a booklet")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "successfully like a booklet",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "User has already liked booklet.",
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
    public ResponseEntity<BaseApiResponse> likeABooklet(HttpServletRequest request, @PathVariable String bookletPublicId){
        String token = request.getHeader("Authorization");
        return bookletService.likeABooklet(token,bookletPublicId);
    }

    @PutMapping("/edit-description/{publicId}")
    @Operation(summary = "Update booklet description")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401",
                    description = "Only teachers and students can call this api.If another user try to call this methode app send 401 code",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}),
            @ApiResponse(responseCode = "403",
                    description = "When the teacher or student who does not own this booklet tries to change the description",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}),
            @ApiResponse(responseCode = "404",
                    description = "Booklet with this public id is not found",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}),
            @ApiResponse(responseCode = "200",
                    description = "Booklet description edited successfully",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}),
            @ApiResponse(responseCode = "500",
                    description = "System Default Exception (SDE), or when database IO exception occurred",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")})})
    public ResponseEntity<BaseApiResponse> editDescription(@PathVariable String publicId, @RequestBody String newDescription, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return bookletService.editDescription(publicId, token, newDescription);
    }

    @GetMapping("/enable-to-edit/{publicId}")
    @Operation(summary = "Specifies whether the user is allowed to change the booklet description or not")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401",
                    description = "Only teachers and students can call this api.If another user try to call this methode or jwt is wrong app send 401 code",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}),
            @ApiResponse(responseCode = "403",
                    description = "When the teacher or student who does not own this booklet tries to change the description",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}),
            @ApiResponse(responseCode = "404",
                    description = "Booklet with this public id is not found",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}),
            @ApiResponse(responseCode = "200",
                    description = "Return old description successfully",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}),
            })
    public ResponseEntity<BaseApiResponse> isEnableToEdit(@PathVariable String publicId,HttpServletRequest request){
        String token = request.getHeader("Authorization");
        return bookletService.isEnableToEdit(publicId,token);
    }
}
