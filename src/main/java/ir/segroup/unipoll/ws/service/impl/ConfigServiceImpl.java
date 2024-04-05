package ir.segroup.unipoll.ws.service.impl;

import ir.segroup.unipoll.config.excel.ExcelHandler;
import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.config.exception.constant.ExceptionMessages;
import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.shared.utils.Util;
import ir.segroup.unipoll.ws.model.entity.UserEntity;
import ir.segroup.unipoll.ws.repository.UserRepository;
import ir.segroup.unipoll.ws.service.ConfigService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ConfigServiceImpl  implements ConfigService {
    private final UserRepository userRepository;
    private final Util util;
    private final ExcelHandler excelHandler;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public ConfigServiceImpl(UserRepository userRepository, Util util, ExcelHandler excelHandler) {
        this.userRepository = userRepository;
        this.util = util;
        this.excelHandler = excelHandler;
    }

    @Override
    public ResponseEntity<BaseApiResponse> init(MultipartFile file)  {
        if (excelHandler.hasExcelFormat(file)) {
            throw new SystemServiceException(ExceptionMessages.NO_EXCEL_FORMAT.getMessage(), HttpStatus.BAD_REQUEST);
        }
        initUser(file);

        return util.createResponse(new ArrayList<>(), HttpStatus.OK);
    }

    private void initUser(MultipartFile file){
        List<UserEntity> newUsers;
        try {
            newUsers = excelHandler.excelToUserEntity(file.getInputStream());
        }catch (IOException e){
            logger.log(Level.OFF,e.getMessage());
            throw new SystemServiceException(ExceptionMessages.INPUT_STREAM_ERROR.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<String> newUsernames = new ArrayList<>(newUsers.stream().map(UserEntity::getUsername).toList());

        List<UserEntity> existedUsers = userRepository.findAll();
        List<String> existedUsernames = existedUsers.stream().map(UserEntity::getUsername).toList();

        newUsernames.removeAll(existedUsernames.stream().filter(newUsernames::contains).toList());
        newUsers.forEach(userEntity -> {
            if (newUsernames.contains(userEntity.getUsername())){
                userRepository.save(userEntity);
            }
        });
    }
}
