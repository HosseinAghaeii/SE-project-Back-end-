package ir.segroup.unipoll.ws.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Builder
@Table(name = "terms")
public class TermEntity implements Serializable {
    @Id
    private Long id;

    private String publicId;

    private String name;

    @OneToMany(mappedBy = "termEntity",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<InstructorCourseCommentEntity> instructorCourseCommentEntities;

    @OneToMany(mappedBy = "termEntity",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<BookletCommentEntity> bookletCommentEntities;

    @OneToMany(mappedBy = "termEntity",fetch = FetchType.LAZY)
    private List<BookletEntity> bookletEntities;
}
