package engine.services;

import engine.models.Quiz;
import engine.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    public void save(Quiz quiz) {
        quizRepository.save(quiz);
    }

    public void delete(Quiz quiz) {
        quizRepository.delete(quiz);
    }

    public Optional<Quiz> findById(Long id) {
        return quizRepository.findById(id);
    }

    public Iterable<Quiz> getAll() {
        return quizRepository.findAll();
    }

    public Page<Quiz> findAllPaging(int page) {
        return quizRepository.findAllPaging(PageRequest.of(page, 10));
    }
}
