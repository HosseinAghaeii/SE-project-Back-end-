package ir.segroup.seproject.ws.model.entity;

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
@Table(name = "colleges")
public class CollegeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String publicId;

    private String name;

    @OneToMany(mappedBy = "collegeEntity",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<AcademicDepartmentEntity> academicDepartmentEntities;
}
