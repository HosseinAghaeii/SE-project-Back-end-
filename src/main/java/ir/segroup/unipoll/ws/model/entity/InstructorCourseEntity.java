package ir.segroup.unipoll.ws.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name ="instructor_course")
public class InstructorCourseEntity implements Serializable {
    @Id
    private long id;

    private String publicId;
    private String lastUpdate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    private CourseEntity courseEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "instructor_id")
    private InstructorEntity instructorEntity;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @OneToMany(mappedBy = "instructorCourseEntity",fetch = FetchType.EAGER)
    private List<BookletEntity> bookletEntities;

    @OneToMany(mappedBy = "instructorCourseEntity",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<RateEntity> rateEntities;

    @OneToMany(mappedBy = "icEntity",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<InstructorCourseCommentEntity> instructorCourseCommentEntities;
}
