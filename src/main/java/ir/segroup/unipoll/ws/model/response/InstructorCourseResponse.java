package ir.segroup.unipoll.ws.model.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class InstructorCourseResponse {
    private String courseName;
    private String instructorCourseFirstname;
    private String instructorCourseLastname;
    private String description;
    private int unit;
    private double rate;
}
