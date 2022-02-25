package engine.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "Completion_history")
public class CompletionHistory {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long completionId;

    @Column
    @JsonProperty("id")
    private long quizId;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column
    @JsonIgnore
    private String username;

    public CompletionHistory() {
    }

    public CompletionHistory(long quizId, String username) {
        this.quizId = quizId;
        this.username = username;
        this.completedAt = LocalDateTime.now();
    }

    public Long getCompletionId() {
        return completionId;
    }

    public long getQuizId() {
        return quizId;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public String getUsername() {
        return username;
    }
}
