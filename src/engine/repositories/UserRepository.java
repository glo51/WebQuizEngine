package engine.repositories;

import engine.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findUserByEmail(String username);
}
