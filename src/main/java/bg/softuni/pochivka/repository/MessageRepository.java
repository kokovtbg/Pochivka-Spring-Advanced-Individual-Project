package bg.softuni.pochivka.repository;

import bg.softuni.pochivka.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByReceiverUsername(String username);

    List<Message> findAllBySenderUsername(String username);

    List<Message> findAllByDateTimeBetween(LocalDateTime minusDays, LocalDateTime now);

    List<Message> findAllBySenderIsNullAndReceiverIsNull();

    void deleteAllBySenderIsNullAndReceiverIsNull();
}
