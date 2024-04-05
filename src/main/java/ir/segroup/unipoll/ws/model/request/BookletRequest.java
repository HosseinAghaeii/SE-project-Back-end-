package ir.segroup.unipoll.ws.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookletRequest {
    private String text;
    private String term;
    private String instCoursePublicId;
}
