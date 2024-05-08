package ir.segroup.unipoll.ws.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.ws.service.TermService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/term")
@Tag(name = "Term controller")
public class TermController {

    private final TermService termService;

    public TermController(TermService termService) {
        this.termService = termService;
    }

    @GetMapping
    public ResponseEntity<BaseApiResponse> getAllTerms(){
        return termService.getAllTerms();
    }

}
