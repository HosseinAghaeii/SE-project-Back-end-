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
    public ResponseEntity<BaseApiResponse> createUser(UserRequest userRequest) {
        Optional<UserEntity> existedUser = userRepository.findByUsername(userRequest.getUsername());
        if (existedUser.isPresent()) {
            throw new SystemServiceException(ExceptionMessages.RECORD_ALREADY_EXISTS.getMessage(), HttpStatus.CONFLICT);
        }
        List<String> validRoles = List.of("ADMIN", "STUDENT", "INSTRUCTOR");
        if (!validRoles.contains(userRequest.getRole())) {
            throw new SystemServiceException(ExceptionMessages.INVALID_ROLE.getMessage(), HttpStatus.BAD_REQUEST);
        }
        UserEntity userEntity = util.convert(userRequest);
        UserEntity savedUser;
        try {
            savedUser = userRepository.save(userEntity);

        } catch (Exception e) {
            logger.log(Level.WARNING, "Problem to write in DB");
            throw new SystemServiceException(ExceptionMessages.DATABASE_IO_EXCEPTION.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UserResponse userResponse = util.convert(savedUser);
        return util.createResponse(userResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<BaseApiResponse> getAllUsers() {
        List<UserResponse> result = userRepository.findAll().stream().map(util::convert).toList();
        return util.createResponse(result,HttpStatus.OK);
    }
}
