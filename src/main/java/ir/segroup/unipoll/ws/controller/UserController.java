package ir.segroup.unipoll.ws.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.ws.model.request.UserRequest;
import ir.segroup.unipoll.ws.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "Users", description = "User endpoints")
@SecurityScheme(
        name = "Authorization",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Adding a new User into database. We can add one of this user type: ADMIN , STUDENT , INSTRUCTOR" +
            ".base on user type that we want to add into db we should fill the attribute of userRequest")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Return a stored User",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "When Role field hase invalid type",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "When the User already exist",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "System Default Exception (SDE), or when database IO exception occurred",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            )

    })
    public ResponseEntity<BaseApiResponse> createUser (@RequestBody UserRequest userRequest){
        return userService.createUser(userRequest);
    }

    @GetMapping
    @Operation(summary = "Getting all users such as admin , student or teacher from database")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "All users successfully returned",
                    content = {@Content(mediaType = "application/json"),@Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "System Default Exception (SDE)",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            )
    })
    public ResponseEntity<BaseApiResponse> getAllUsers(){
        return userService.getAllUsers();
    }


}
