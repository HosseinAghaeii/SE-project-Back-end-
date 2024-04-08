package ir.segroup.unipoll.ws.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "academic_departments")
public class AcademicDepartmentEntity implements Serializable {
    @Id
    private long id;

    private String name;

    private String publicId;

    private String description;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "college_id")
    private CollegeEntity collegeEntity;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "academicDept_course",
            joinColumns = @JoinColumn(name = "academicDept_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<CourseEntity> courseEntities;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "academicDept_instructor",
            joinColumns = @JoinColumn(name = "academicDept_id"),
            inverseJoinColumns = @JoinColumn(name = "instructor_id")
    )
    private List<InstructorEntity> instructorEntities;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "manager_id")
    private InstructorEntity manager;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "assistant_id")
    private InstructorEntity assistant;

}
