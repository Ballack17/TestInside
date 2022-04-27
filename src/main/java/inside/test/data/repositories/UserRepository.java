package inside.test.data.repositories;

import inside.test.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUsersByName(String name);
    Boolean existsByName(String name);
}
