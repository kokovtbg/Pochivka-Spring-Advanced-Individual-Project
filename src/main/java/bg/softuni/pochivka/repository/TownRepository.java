package bg.softuni.pochivka.repository;

import bg.softuni.pochivka.model.entity.Town;
import bg.softuni.pochivka.model.enums.TownEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TownRepository extends JpaRepository<Town, Long> {
    Town findByName(TownEnum townEnum);
}
