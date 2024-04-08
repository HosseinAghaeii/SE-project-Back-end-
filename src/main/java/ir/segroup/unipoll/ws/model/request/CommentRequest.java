package ir.segroup.unipoll.ws.model.request;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequest {

    private String firstname;

    private String lastname;

    private String email;

    private  String text;
}
