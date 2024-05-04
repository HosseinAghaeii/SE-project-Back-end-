package ir.segroup.unipoll.ws.model.response;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentCResponse {
    private String text;
    private String publicId;
    private String createdDate;
    private String writerName;
    private String term;
}
