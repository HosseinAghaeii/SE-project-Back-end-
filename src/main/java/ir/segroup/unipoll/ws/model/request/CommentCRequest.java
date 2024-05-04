package ir.segroup.unipoll.ws.model.request;

import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentCRequest {
    private String text;
    private String icPublicId;
    private String termPublicId;

}
