package ir.segroup.unipoll.ws.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "students")
@PrimaryKeyJoinColumn(name = "std_id")
public class StudentEntity  extends  UserEntity{

    private String major;

    @OneToMany(mappedBy = "studentEntity",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<RateEntity> rateEntities; // the list of all rate of student

    public StudentEntity(String firstname, String lastname, String username, String encryptedPassword, String role, String major) {
        super(firstname, lastname, username, encryptedPassword, role);
        this.major = major;
    }
}
