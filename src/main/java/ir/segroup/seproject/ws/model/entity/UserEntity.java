package ir.segroup.seproject.ws.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstname;

    private String lastname;

    private String username;

    @Column(name = "password")
    private String encryptedPassword;

    private String role;

    private Date birthDate;

    private String email;

    private String major;

    private String profilePhoto;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name = "favorite_booklets",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "favorite_booklet_id")
    )
    private List<BookletEntity> favoriteBooklets;

    @ManyToMany(mappedBy = "likes",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<BookletEntity> likedBooklets;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name = "uploaded_booklets",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "booklet_id")
    )
    private List<BookletEntity> uploadedBooklets;

    @OneToMany(mappedBy = "userEntity",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<CommentBEntity> commentBEntities;

    @OneToMany(mappedBy = "userEntity",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<CommentCEntity> commentCEntities;

    //================================================ Teacher Fields

    @OneToMany(mappedBy = "instructorEntity",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<InstructorCourseEntity> icEntities;

    @ManyToMany(mappedBy = "instructorEntities",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<AcademicDepartmentEntity> academicDeptEntities; // list of academic dept that instructor worked on it.

    //================================================ Student Fields

    @OneToMany(mappedBy = "studentEntity",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<RateEntity> rateEntities; // the list of all rate of student


}

