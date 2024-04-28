package ir.segroup.unipoll.ws.model.response;

import jakarta.persistence.Column;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private String firstname;
    private String lastname;
    private String publicId;
    private String username;
    private String role;


    public UserResponse(String firstname, String lastname, String publicId, String role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.publicId = publicId;
        this.role = role;
    }
}
