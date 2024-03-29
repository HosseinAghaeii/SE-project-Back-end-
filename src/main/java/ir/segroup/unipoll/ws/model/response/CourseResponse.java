package ir.segroup.unipoll.ws.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CourseResponse {
    private String name;
    private int unit;
    private String publicId;
}
