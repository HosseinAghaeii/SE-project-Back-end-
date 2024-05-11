package ir.segroup.unipoll.ws.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "terms")
public class TermEntity implements Serializable {
    @Id
    private Long id;

    private String publicId;

    private String name;

    @OneToMany(mappedBy = "termEntity",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<CommentCEntity> commentCEntities;

    @OneToMany(mappedBy = "termEntity",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<BookletEntity> bookletEntities;
}
