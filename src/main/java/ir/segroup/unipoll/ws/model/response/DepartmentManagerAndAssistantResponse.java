package ir.segroup.unipoll.ws.model.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentManagerAndAssistantResponse {
    private String type;
    private String publicId;
    private String firstname;
    private String lastname;
    private String profilePhoto;
    private String phd;
    private String academicRank;
    private String phoneNumber;
    private String email;
    private String websiteLink;
}
