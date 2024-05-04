package ir.segroup.unipoll.ws.repository;

import ir.segroup.unipoll.ws.model.entity.CommentCEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentCRepository extends JpaRepository<CommentCEntity,Long> {
}
