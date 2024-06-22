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
@Table(name = "colleges")
public class CollegeEntity implements Serializable {
    @Id
    private long id;

    private String publicId;

    private String name;

    @OneToMany(mappedBy = "collegeEntity",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<AcademicDepartmentEntity> academicDepartmentEntities;
}
