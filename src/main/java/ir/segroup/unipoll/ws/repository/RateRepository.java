package ir.segroup.unipoll.ws.repository;

import ir.segroup.unipoll.ws.model.entity.InstructorCourseEntity;
import ir.segroup.unipoll.ws.model.entity.RateEntity;
import ir.segroup.unipoll.ws.model.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<RateEntity, Long> {

    Optional<RateEntity> findByStudentEntityAndInstructorCourseEntity(StudentEntity studentEntity, InstructorCourseEntity instructorCourseEntity);

    List<RateEntity> findByInstructorCourseEntity(InstructorCourseEntity instructorCourseEntity);
}
