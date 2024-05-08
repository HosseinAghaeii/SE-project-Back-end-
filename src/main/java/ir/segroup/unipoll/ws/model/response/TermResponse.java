package ir.segroup.unipoll.ws.model.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class TermResponse {
    private String name;
    private String publicId;
}
