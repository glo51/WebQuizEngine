package engine.models;

import javax.validation.constraints.NotBlank;
import java.util.List;


public class Answer {

    @NotBlank
    List<Integer> answer;

    public Answer() {
    }

    public List<Integer> getAnswer() {
        return answer;
    }
}
