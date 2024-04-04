package ir.segroup.unipoll.ws.repository;

import ir.segroup.unipoll.ws.model.entity.InstructorCourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstructorCourseRepository extends JpaRepository<InstructorCourseEntity, Long> , JpaSpecificationExecutor<InstructorCourseEntity> {
    Optional<InstructorCourseEntity> findByPublicId(String publicId);
}
