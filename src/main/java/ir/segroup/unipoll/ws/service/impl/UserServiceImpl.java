package ir.segroup.unipoll.ws.service.impl;

import ir.segroup.unipoll.config.exception.SystemServiceException;
import ir.segroup.unipoll.config.exception.constant.ExceptionMessages;
import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.shared.utils.UserUtil;
import ir.segroup.unipoll.ws.model.entity.UserEntity;
import ir.segroup.unipoll.ws.model.request.UserRequest;
import ir.segroup.unipoll.ws.model.response.UserResponse;
import ir.segroup.unipoll.ws.repository.UserRepository;
import ir.segroup.unipoll.ws.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserUtil util;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public UserServiceImpl(UserRepository userRepository, UserUtil convertor) {
        this.userRepository = userRepository;

        this.util = convertor;
    }

    @Override
    public ResponseEntity<BaseApiResponse> getAllUsers() {
        List<UserResponse> result = userRepository.findAll().stream().map(util::convert).toList();
        return util.createResponse(result,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseApiResponse> getOneUser(String token) {

        String username = util.getUsernameFromToken(token);
        UserResponse response = userRepository.findByUsername(username).map(util::convert)
                .orElseThrow(() -> {
                    logger.log(Level.OFF, "Failed to get user details");
                    return new SystemServiceException(ExceptionMessages.NO_RECORD_FOUND.getMessage(), HttpStatus.NOT_FOUND);
                });
        return util.createResponse(response,HttpStatus.OK);
    }
}
