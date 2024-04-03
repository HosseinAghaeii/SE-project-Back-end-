package ir.segroup.unipoll.ws.model.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentResponse {
    private String publicId;
    private String name;
    private String description;
}
