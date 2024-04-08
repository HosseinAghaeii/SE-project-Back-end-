package ir.segroup.unipoll.ws.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.ws.model.request.BookletRequest;
import ir.segroup.unipoll.ws.repository.BookletRepository;
import ir.segroup.unipoll.ws.service.BookletService;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
                    description = "Error in get data -array of bytes- from MultipartFile",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "201",
                    description = "Return a stored booklet with a path",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "System Default Exception (SDE), or when database IO exception occurred",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            )
    })
    public ResponseEntity<BaseApiResponse> uploadBooklet(@RequestParam("file") MultipartFile booklet,
                                                         @ModelAttribute("bookletRequest") BookletRequest bookletRequest){
        return bookletService.uploadBooklet(booklet, bookletRequest);
    }

    @GetMapping
    public ResponseEntity<BaseApiResponse> getTenTopBooklets(HttpServletRequest request) {
        String token = request.getHeader("Athorization");
        return bookletService.getTenTopBooklets(token);
    }



}
