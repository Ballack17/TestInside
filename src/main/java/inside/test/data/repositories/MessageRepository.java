package inside.test.data.repositories;

import inside.test.data.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByUser_NameOrderByCreatedAtDesc(String name);
}
