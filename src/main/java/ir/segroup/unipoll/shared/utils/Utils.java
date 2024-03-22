package ir.segroup.unipoll.shared.utils;

import ir.segroup.unipoll.shared.model.BaseApiResponse;
import ir.segroup.unipoll.ws.model.entity.InstructorEntity;
import ir.segroup.unipoll.ws.model.entity.StudentEntity;
import ir.segroup.unipoll.ws.model.entity.UserEntity;
import ir.segroup.unipoll.ws.model.request.UserRequest;
import ir.segroup.unipoll.ws.model.response.InstructorResponse;
import ir.segroup.unipoll.ws.model.response.StudentResponse;
import ir.segroup.unipoll.ws.model.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Utils {

    private static final String ADMIN= "ADMIN";
    private static final String STUDENT = "STUDENT";
    private static final String INSTRUCTOR = "INSTRUCTOR";

    private final PasswordEncoder passwordEncoder;

    public Utils(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity convert(UserRequest userRequest) {
        UserEntity result;

        switch (userRequest.getRole()) {
            case ADMIN: {
                result = new UserEntity(userRequest.getFirstname(),
                        userRequest.getLastname(),
                        userRequest.getUsername(),
                        passwordEncoder.encode(userRequest.getPassword()),
                        userRequest.getRole());
            }
            break;
            case STUDENT: {
                result = new StudentEntity(userRequest.getFirstname(),
                        userRequest.getLastname(),
                        userRequest.getUsername(),
                        passwordEncoder.encode(userRequest.getPassword()),
                        userRequest.getRole(),
                        userRequest.getMajor());
            }
            break;
            case INSTRUCTOR: {
                result = new InstructorEntity(userRequest.getFirstname(),
                        userRequest.getLastname(),
                        userRequest.getUsername(),
                        passwordEncoder.encode(userRequest.getPassword()),
                        userRequest.getRole(),
                        userRequest.getPhd(),
                        userRequest.getAcademicRank(),
                        userRequest.getPhoneNumber(),
                        userRequest.getEmail(),
                        userRequest.getWebsiteLink());
            }
            break;
            default:
                result = null;
                break;
        }
        return result;
    }

    public UserResponse convert(UserEntity userEntity) {

        UserResponse result;
        switch (userEntity.getRole()) {
            case ADMIN:
                result = new UserResponse(userEntity.getUsername(), userEntity.getFirstname(), userEntity.getLastname(), userEntity.getRole());
            break;
            case STUDENT: {
                StudentEntity studentEntity = (StudentEntity) userEntity;
                result = new StudentResponse(studentEntity.getUsername(),
                        studentEntity.getFirstname(),
                        studentEntity.getLastname(),
                        studentEntity.getRole(),
                        studentEntity.getMajor());
            }
            break;
            case INSTRUCTOR:{
                InstructorEntity insEntity = (InstructorEntity) userEntity;
                result = new InstructorResponse(insEntity.getUsername(),
                        insEntity.getFirstname(),
                        insEntity.getLastname(),
                        insEntity.getRole(),
                        insEntity.getPhd(),
                        insEntity.getAcademicRank(),
                        insEntity.getPhoneNumber(),
                        insEntity.getEmail(),
                        insEntity.getWebsiteLink());
            }
            break;
            default: result = null;
            break;
        }
        return result;
    }

    public ResponseEntity<BaseApiResponse> createResponse(Object object, HttpStatus httpStatus){
        BaseApiResponse baseApiResponse = BaseApiResponse.builder()
                .action(true)
                .date(new Date())
                .message("")
                .result(object)
                .build();
        return new ResponseEntity<>(baseApiResponse, httpStatus);
    }

}
