package ir.segroup.unipoll.ws.repository;

import ir.segroup.unipoll.ws.model.entity.InstructorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InstructorRepository extends JpaRepository<InstructorEntity, Long>, JpaSpecificationExecutor<InstructorEntity> {
}
