package ir.segroup.unipoll.ws.repository;

import ir.segroup.unipoll.ws.model.entity.BookletCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookletCommentRepository extends JpaRepository<BookletCommentEntity,Long> {
}
