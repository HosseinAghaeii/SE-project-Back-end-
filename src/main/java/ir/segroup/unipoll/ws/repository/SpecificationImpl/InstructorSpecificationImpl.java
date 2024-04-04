package ir.segroup.unipoll.ws.repository.SpecificationImpl;

import ir.segroup.unipoll.ws.model.entity.InstructorEntity;
import org.springframework.data.jpa.domain.Specification;

public class InstructorSpecificationImpl {
    public InstructorSpecificationImpl() {}
    public static Specification<InstructorEntity> fromInstructorFirstname(String firstname) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("firstname"), "%" + firstname + "%");
    }
    public static Specification<InstructorEntity> fromInstructorLastname(String lastname) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("lastname"), "%" + lastname + "%");
    }
}
