package ir.segroup.unipoll.ws.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "Create User", description = "Model for create User")
public class UserRequest {

    @Schema(description = "Firstname for user", example = "Kamal")
    private String firstname;

    @Schema(description = "Lastname for user", example = "Jamshidi")
    private String lastname;

    @Schema(description = "Username for user.This field must be uniq", example = "Jamshidi_12")
    private String username;

    @Schema(description = "Password for user", example = "123")
    private String password;

    @Schema(description = "This field only accept: ADMIN , STUDENT or INSTRUCTOR", example = "INSTRUCTOR")
    private String role;

    //==============================student fields

    @Schema(description = "major for student", example = "computer science")
    private String major;

    //==============================instructor fields

    @Schema(description = "This field show that this instructor study in witch field", example = "computer science")
    private String phd;

    @Schema(description = "academicRank for instructor",example = "Ostad yar")
    private String academicRank;

    @Schema(description = "phoneNumber for instructor",example = "09905866512")
    private String phoneNumber;

    @Schema(description = "Email for instructor", example = "kamal@jmail.com")
    private String email;

    @Schema(description = "websiteLink for instructor", example = "http://kmjm.ir")
    private String websiteLink;

}
