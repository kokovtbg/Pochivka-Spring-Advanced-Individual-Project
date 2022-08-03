package bg.softuni.pochivka.repository;

import bg.softuni.pochivka.model.entity.Region;
import bg.softuni.pochivka.model.enums.RegionEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    Region findByTypeRegion(RegionEnum sea);
}
