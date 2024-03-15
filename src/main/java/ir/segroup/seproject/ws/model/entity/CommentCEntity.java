package ir.segroup.seproject.ws.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course_comment")
public class CommentCEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String publicId;

    private String text;

    private String term; // ##-#   year-1 OR 2

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ic_id")
    private InstructorCourseEntity icEntity;
}
