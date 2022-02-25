package engine.controllers;

import engine.models.Answer;
import engine.models.CompletionHistory;
import engine.models.Quiz;
import engine.models.User;
import engine.services.CompletionHistoryService;
import engine.services.QuizService;
import engine.services.UserDetailsServiceImpl;
import engine.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;


@RestController
public class MainController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private CompletionHistoryService completionHistoryService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/api/quizzes/{id}")
    public ResponseEntity<?> getQuiz(@PathVariable long id) {

        Optional<Quiz> searchedQuiz = quizService.findById(id);
        if (searchedQuiz.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(searchedQuiz, HttpStatus.OK);
    }

    @GetMapping("/api/quizzes")
    public Page<Quiz> getQuizzes(@RequestParam(defaultValue = "0") int page) {

        return quizService.findAllPaging(page);
    }

    @GetMapping("/api/quizzes/completed")
    public Page<CompletionHistory> getCompleted(@RequestParam(defaultValue = "0") int page,
                                                @AuthenticationPrincipal UserDetails user)
    {
        return completionHistoryService.findByUserPaging(user.getUsername(), page);
    }


    @PostMapping("/api/quizzes")
    public Quiz postQuiz(@RequestBody @Valid Quiz input, @AuthenticationPrincipal UserDetails user) {

        input.setAuthorUsername(user.getUsername());
        quizService.save(input);
        return input;
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public ResponseEntity<?> solveQuiz(@PathVariable long id, @RequestBody Answer inputAnswer,
                                       @AuthenticationPrincipal UserDetails user)
    {
        Optional<Quiz> searchedQuiz = quizService.findById(id);
        if (searchedQuiz.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Map<String, Object> response = new LinkedHashMap<>();
        if (inputAnswer.getAnswer().equals(searchedQuiz.get().getAnswer())) {
            response.put("success", true);
            response.put("feedback", "Congratulations, you're right!");
            CompletionHistory completion = new CompletionHistory(id, user.getUsername());
            completionHistoryService.save(completion);
        } else {
            response.put("success", false);
            response.put("feedback", "Wrong answer! Please, try again.");
        }

        return new ResponseEntity<Map<String, ?>>(response, HttpStatus.OK);
    }

    @PostMapping("/api/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {

        try {
            userDetailsService.loadUserByUsername(user.getEmail());
            return ResponseEntity.badRequest().build();
        } catch (UsernameNotFoundException e) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.save(user);
            return ResponseEntity.ok().build();
        }
    }


    @DeleteMapping("/api/quizzes/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable long id, @AuthenticationPrincipal UserDetails user) {

        Optional<Quiz> quiz = quizService.findById(id);
        if (quiz.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (!user.getUsername().equals(quiz.get().getAuthorUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        quizService.delete(quiz.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
