package engine.services;

import engine.models.CompletionHistory;
import engine.repositories.CompletionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CompletionHistoryService {

    @Autowired
    CompletionHistoryRepository completionHistoryRepository;

    public void save(CompletionHistory completionHistory) {
        completionHistoryRepository.save(completionHistory);
    }

    public Page<CompletionHistory> findByUserPaging(String username, int page) {
        return completionHistoryRepository.findByUserPaging(
                username,
                PageRequest.of(page, 10, Sort.by("completed_at").descending())
        );
    }
}
