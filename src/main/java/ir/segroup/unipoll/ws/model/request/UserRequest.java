package ir.segroup.unipoll.ws.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    private String firstname;

    private String lastname;

    private String username;

    private String password;

    private String role;

    //==============================student fields

    private String major;

    //==============================instructor fields

    private String phd;

    private String academicRank;

    private String phoneNumber;

    private String email;

    private String websiteLink;

}
