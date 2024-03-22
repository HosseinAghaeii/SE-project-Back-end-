package ir.segroup.unipoll.ws.repository;

import ir.segroup.unipoll.ws.model.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity,Long> {
    Optional<StudentEntity> findByUsername(String userName);
}
