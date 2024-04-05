package ir.segroup.unipoll.ws.model.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentCourseResponse {
    private String publicId;
    private String courseName;
}
