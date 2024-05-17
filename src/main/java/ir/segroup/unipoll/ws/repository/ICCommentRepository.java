package ir.segroup.unipoll.ws.repository;

import ir.segroup.unipoll.ws.model.entity.ICCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICCommentRepository extends JpaRepository<ICCommentEntity,Long> {
}
