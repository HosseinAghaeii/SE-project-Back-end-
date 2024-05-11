package ir.segroup.unipoll.ws.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "course_comments")
public class CommentCEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String publicId;

    @Column(columnDefinition = "LONGTEXT")
    private String text;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "term_id")
    private TermEntity termEntity;

    private Date createdDate;

    @Column(nullable = false)
    private boolean isUnknown;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ic_id")
    private InstructorCourseEntity icEntity;
}
