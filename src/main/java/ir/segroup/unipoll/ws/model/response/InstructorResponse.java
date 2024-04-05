package ir.segroup.unipoll.ws.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class InstructorResponse extends UserResponse{
    private String phd;

    private String academicRank;

    private String phoneNumber;

    private String email;

    private String websiteLink;

    public InstructorResponse(String username,
                              String firstname,
                              String lastname,
                              String role,
                              String phd,
                              String academicRank,
                              String phoneNumber,
                              String email,
                              String websiteLink) {
        super(username, firstname, lastname, role);
        this.phd = phd;
        this.academicRank = academicRank;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.websiteLink = websiteLink;
    }
    public InstructorResponse(String firstname,
                              String lastname,
                              String phd,
                              String academicRank,
                              String phoneNumber,
                              String email,
                              String websiteLink) {
        super(firstname, lastname);
        this.phd = phd;
        this.academicRank = academicRank;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.websiteLink = websiteLink;
    }
}
