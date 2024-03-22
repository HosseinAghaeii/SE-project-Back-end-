package ir.segroup.unipoll.ws.controller;

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
public class LoginController {

    @GetMapping
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
