package ir.segroup.unipoll.ws.repository;

import ir.segroup.unipoll.ws.model.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity,Long> {
    Optional<CourseEntity> findByName(String name);
}
