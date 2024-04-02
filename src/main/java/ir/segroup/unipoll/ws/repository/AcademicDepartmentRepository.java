package ir.segroup.unipoll.ws.repository;

import ir.segroup.unipoll.ws.model.entity.AcademicDepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AcademicDepartmentRepository extends JpaRepository<AcademicDepartmentEntity, Long> {
    Optional<AcademicDepartmentEntity> findByName(String name);
}
