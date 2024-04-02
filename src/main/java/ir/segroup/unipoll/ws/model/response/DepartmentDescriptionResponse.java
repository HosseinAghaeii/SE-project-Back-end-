package ir.segroup.unipoll.ws.model.response;

import ir.segroup.unipoll.ws.model.entity.CourseEntity;
import ir.segroup.unipoll.ws.model.entity.InstructorEntity;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentDescriptionResponse {
    private String publicId;
    private String name;
    private String description;
}
