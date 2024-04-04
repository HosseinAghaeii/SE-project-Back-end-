package ir.segroup.unipoll.ws.model.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookletResponse {
    private String publicId;
    private String courseName;
    private String instructorFirstname;
    private String instructorLastname;
    private String uploaderFirstname;
    private String uploaderLastname;
    private String term;
    private int likeNumber;
    private boolean saved;
    private boolean liked;
}
