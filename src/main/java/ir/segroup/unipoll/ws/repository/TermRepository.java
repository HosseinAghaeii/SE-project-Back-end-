package ir.segroup.unipoll.ws.repository;

import ir.segroup.unipoll.ws.model.entity.TermEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TermRepository extends JpaRepository<TermEntity,Long> {
    Optional<TermEntity> findByPublicId(String publicId);
}
