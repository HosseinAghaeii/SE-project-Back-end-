package ir.segroup.unipoll.ws.controller;

import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.ws.service.ConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/config")
public class ConfigController {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final ConfigService configService;

    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    @PostMapping()
    public ResponseEntity<BaseApiResponse> initDb(@RequestParam("file") MultipartFile file)  {
        ResponseEntity<BaseApiResponse> result = configService.init(file);
        logger.log(Level.INFO,"Excel file push in DB successfully. file name is: {0}",file.getName());
        return result;
    }
}
