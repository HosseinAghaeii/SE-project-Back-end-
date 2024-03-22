package ir.segroup.unipoll.ws.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.segroup.unipoll.shared.model.BaseApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/login")
@Tag(name = "Login controller")
public class LoginController {

    @GetMapping
    @Operation(summary = "This is methode for login with username and password")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "One user successfully login and get his JWT token in header of response.",
                    content = {@Content(mediaType = "application/json"),@Content(mediaType = "application/xml")}
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "username or password are invalid.",
                    content = {@Content(mediaType = "application/json"), @Content(mediaType = "application/xml")}
            )
    })
    public ResponseEntity<BaseApiResponse> getStatusToTest(){
        BaseApiResponse response = BaseApiResponse.builder()
                .action(true)
                .date(new Date())
                .message("it works ;)")
                .result(new ArrayList<>())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
