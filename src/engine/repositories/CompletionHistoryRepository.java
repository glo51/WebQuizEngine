package engine.repositories;

import engine.models.CompletionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface CompletionHistoryRepository extends CrudRepository<CompletionHistory, Long> {

    @Query(value = "SELECT * FROM Completion_history WHERE username = :username", nativeQuery = true)
    Page<CompletionHistory> findByUserPaging(@Param("username") String username, Pageable pageable);
}
