package bg.softuni.pochivka.repository;

import bg.softuni.pochivka.model.entity.Comfort;
import bg.softuni.pochivka.model.enums.ComfortEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComfortRepository extends JpaRepository<Comfort, Long> {
    Comfort findByName(ComfortEnum comfortEnum);
}
