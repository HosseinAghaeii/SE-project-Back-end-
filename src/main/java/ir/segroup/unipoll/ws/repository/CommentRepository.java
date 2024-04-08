package ir.segroup.unipoll.ws.repository;

import ir.segroup.unipoll.ws.model.entity.CommentEntity;
import ir.segroup.unipoll.ws.model.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Optional<CommentEntity> findByPublicId(String publicId);
}
