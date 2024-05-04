package ir.segroup.unipoll.ws.model.response;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateICDescriptionResponse {
    private String lastUpdate;
    private String description;
}
