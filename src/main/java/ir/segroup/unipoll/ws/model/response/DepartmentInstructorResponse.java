package ir.segroup.unipoll.ws.model.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentInstructorResponse {
    private String username;
    private String firstname;
    private String lastname;
    private String profilePhoto;
    private String academicRank;
}
