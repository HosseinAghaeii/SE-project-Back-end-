package ir.segroup.unipoll.ws.repository.SpecificationImpl;

import ir.segroup.unipoll.ws.model.entity.InstructorCourseEntity;
import org.springframework.data.jpa.domain.Specification;


public class InstructorCourseSpecificationImpl {
    private InstructorCourseSpecificationImpl() {}
    public static Specification<InstructorCourseEntity> fromCourseName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("courseEntity").get("name"), "%" + name + "%");
    }
}
