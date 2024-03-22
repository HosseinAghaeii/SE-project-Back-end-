package ir.segroup.unipoll.ws.controller;

import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.ws.model.request.UserRequest;
import ir.segroup.unipoll.ws.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<BaseApiResponse> createUser (@RequestBody UserRequest userRequest){
        return userService.createUser(userRequest);
    }

    @GetMapping("")
    public ResponseEntity<BaseApiResponse> getAllUsers(){
        return userService.getAllUsers();
    }


}
