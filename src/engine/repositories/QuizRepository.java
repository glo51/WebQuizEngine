package engine.repositories;

import engine.models.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface QuizRepository extends CrudRepository<Quiz, Long> {

    @Query(value = "SELECT * FROM Quizzes", nativeQuery = true)
    Page<Quiz> findAllPaging(Pageable pageable);
}