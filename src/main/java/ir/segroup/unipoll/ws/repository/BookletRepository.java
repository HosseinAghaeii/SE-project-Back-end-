package ir.segroup.unipoll.ws.repository;

import ir.segroup.unipoll.ws.model.entity.BookletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookletRepository extends JpaRepository<BookletEntity, Long> {
    Optional<BookletEntity> findByPublicId(String publicId);
}
