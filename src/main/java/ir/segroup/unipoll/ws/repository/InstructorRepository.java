package ir.segroup.unipoll.ws.repository;

import ir.segroup.unipoll.ws.model.entity.InstructorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface InstructorRepository extends JpaRepository<InstructorEntity, Long>, JpaSpecificationExecutor<InstructorEntity> {
        Optional<InstructorEntity> findByPublicId(String publicId);
}
