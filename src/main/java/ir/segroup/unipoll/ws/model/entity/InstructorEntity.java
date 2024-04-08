package ir.segroup.unipoll.ws.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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

    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AcademicDepartmentEntity> academicDepForManagers; // list of academic dept that instructor has role as manager.

    @OneToMany(mappedBy = "assistant", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AcademicDepartmentEntity> academicDepForAssistants; // list of academic dept that instructor has a role as assistant.
}
