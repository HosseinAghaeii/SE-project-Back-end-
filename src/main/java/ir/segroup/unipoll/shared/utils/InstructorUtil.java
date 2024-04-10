package ir.segroup.unipoll.shared.utils;

import ir.segroup.unipoll.ws.model.entity.InstructorEntity;
import ir.segroup.unipoll.ws.model.response.InstructorResponse;
import org.springframework.stereotype.Component;

@Component
public class InstructorUtil extends Util{
    public InstructorResponse convert(InstructorEntity instructor) {
        return new InstructorResponse(instructor.getFirstname(),
                instructor.getLastname(),
                instructor.getPhd(),
                instructor.getAcademicRank(),
                instructor.getPhoneNumber(),
                instructor.getEmail(),
                instructor.getWebsiteLink(),
                instructor.getProfilePhoto());
    }

}
