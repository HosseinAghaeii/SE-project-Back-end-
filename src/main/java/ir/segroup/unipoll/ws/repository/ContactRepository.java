package ir.segroup.unipoll.ws.repository;

import ir.segroup.unipoll.ws.model.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, Long> {
    Optional<ContactEntity> findByPublicId(String publicId);
}
