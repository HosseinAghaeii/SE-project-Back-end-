package ir.segroup.unipoll.shared.utils;

import ir.segroup.unipoll.ws.model.entity.InstructorEntity;
import ir.segroup.unipoll.ws.model.entity.StudentEntity;
import ir.segroup.unipoll.ws.model.entity.UserEntity;
import ir.segroup.unipoll.ws.model.request.UserRequest;
import ir.segroup.unipoll.ws.model.response.InstructorResponse;
import ir.segroup.unipoll.ws.model.response.StudentResponse;
import ir.segroup.unipoll.ws.model.response.UserResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserUtil extends Util{

    private static final String ADMIN= "ADMIN";
    private static final String STUDENT = "STUDENT";
    private static final String INSTRUCTOR = "INSTRUCTOR";


    public UserResponse convert(UserEntity userEntity) {

        UserResponse result;
        switch (userEntity.getRole()) {
            case ADMIN:
                result = new UserResponse(userEntity.getFirstname(),userEntity.getLastname(),userEntity.getPublicId(),userEntity.getUsername(),userEntity.getRole());
            break;
            case STUDENT: {
                StudentEntity studentEntity = (StudentEntity) userEntity;
                result = new StudentResponse(
                        studentEntity.getFirstname(),
                        studentEntity.getLastname(),
                        studentEntity.getRole(),
                        studentEntity.getMajor(),
                        studentEntity.getPublicId());
            }
            break;
            case INSTRUCTOR:{
                InstructorEntity insEntity = (InstructorEntity) userEntity;
                result = new InstructorResponse(insEntity.getPublicId(),
                        insEntity.getFirstname(),
                        insEntity.getLastname(),
                        insEntity.getRole(),
                        insEntity.getPhd(),
                        insEntity.getAcademicRank(),
                        insEntity.getPhoneNumber(),
                        insEntity.getEmail(),
                        insEntity.getWebsiteLink(),
                        insEntity.getPublicId());
            }
            break;
            default: result = null;
            break;
        }
        return result;
    }
}
