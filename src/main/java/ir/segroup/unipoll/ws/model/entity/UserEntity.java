package ir.segroup.unipoll.ws.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity implements Serializable {
    @Id
    private long id;

    private String publicId;

    private String firstname;

    private String lastname;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(name = "password")
    private String encryptedPassword;

    private String role;

    private String profilePhoto;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "favorite_booklets",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "favorite_booklet_id")
    )
    private List<BookletEntity> favoriteBooklets;

    @ManyToMany(mappedBy = "likes",fetch = FetchType.LAZY)
    private List<BookletEntity> likedBooklets;

    @OneToMany(mappedBy = "uploaderUser",fetch = FetchType.EAGER)
    private List<BookletEntity> uploadedBooklets;

    @OneToMany(mappedBy = "userEntity",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<BookletCommentEntity> commentBEntities;

    @OneToMany(mappedBy = "userEntity",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<ICCommentEntity> commentCEntities;

    public UserEntity(String id,String firstname, String lastname, String username, String encryptedPassword, String role) {
        this.id = Long.parseLong(id);
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.role = role;
    }
}

