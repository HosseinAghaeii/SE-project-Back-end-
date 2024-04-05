package ir.segroup.unipoll.ws.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "instructors")
@PrimaryKeyJoinColumn(name = "ins_id")
public class InstructorEntity extends UserEntity{

    private String phd;

    private String academicRank;

    private String phoneNumber;

    private String email;

    private String websiteLink;

    @OneToMany(mappedBy = "instructorEntity",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<InstructorCourseEntity> icEntities;

    @ManyToMany(mappedBy = "instructorEntities",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<AcademicDepartmentEntity> academicDeptEntities; // list of academic dept that instructor worked on it.

}
