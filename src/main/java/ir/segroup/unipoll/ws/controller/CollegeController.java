package ir.segroup.unipoll.ws.controller;

import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.ws.model.request.CollegeRequest;
import ir.segroup.unipoll.ws.service.CollegeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/college")
public class CollegeController {
    private final CollegeService collegeService;

    public CollegeController(CollegeService collegeService) {
        this.collegeService = collegeService;
    }

    @PostMapping
    public ResponseEntity<BaseApiResponse> createCollege(@RequestBody CollegeRequest collegeRequest){
        return collegeService.createCollege(collegeRequest);

    }

}
