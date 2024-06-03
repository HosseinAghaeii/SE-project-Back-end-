package ir.segroup.unipoll.ws.model.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookletCommentRequest {
    private String text;
    private String bookletPublicId;
    private String termPublicId;
    private boolean unknown;
}
