package ir.segroup.unipoll.ws.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class StudentResponse extends UserResponse {
    private String major;

    public StudentResponse(String firstname, String lastname, String role, String major,String publicId) {
        super(firstname,lastname,publicId,role);
        this.major = major;
    }
}
