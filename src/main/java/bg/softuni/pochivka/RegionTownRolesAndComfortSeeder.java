package bg.softuni.pochivka;

import bg.softuni.pochivka.model.entity.Comfort;
import bg.softuni.pochivka.model.entity.Region;
import bg.softuni.pochivka.model.entity.Role;
import bg.softuni.pochivka.model.entity.Town;
import bg.softuni.pochivka.model.enums.ComfortEnum;
import bg.softuni.pochivka.model.enums.RegionEnum;
import bg.softuni.pochivka.model.enums.TownEnum;
import bg.softuni.pochivka.model.enums.UserRoles;
import bg.softuni.pochivka.repository.ComfortRepository;
import bg.softuni.pochivka.repository.RegionRepository;
import bg.softuni.pochivka.repository.RoleRepository;
import bg.softuni.pochivka.repository.TownRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class RegionTownRolesAndComfortSeeder implements CommandLineRunner {
    private RegionRepository regionRepository;
    private TownRepository townRepository;
    private ComfortRepository comfortRepository;
    private RoleRepository roleRepository;

    @Autowired
    public RegionTownRolesAndComfortSeeder(RegionRepository regionRepository,
                                           TownRepository townRepository,
                                           ComfortRepository comfortRepository,
                                           RoleRepository roleRepository) {
        this.regionRepository = regionRepository;
        this.townRepository = townRepository;
        this.comfortRepository = comfortRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (this.regionRepository.count() == 0) {
            Arrays.stream(RegionEnum.values())
                    .map(Region::new)
                    .forEach(r -> this.regionRepository.save(r));
        }
        if (this.townRepository.count() == 0) {

            TownEnum[] townEnums = TownEnum.values();
            for (int i = 0; i < townEnums.length; i++) {
                Town town = new Town();
                town.setName(townEnums[i]);
                if (i <= 11) {
                    town.setRegion(this.regionRepository.findByTypeRegion(RegionEnum.SEA));
                } else if (i <= 21) {
                    town.setRegion(this.regionRepository.findByTypeRegion(RegionEnum.MOUNTAIN));
                } else {
                    town.setRegion(this.regionRepository.findByTypeRegion(RegionEnum.OTHER));
                }
                this.townRepository.save(town);
            }
        }
        if (this.comfortRepository.count() == 0) {
            Arrays.stream(ComfortEnum.values())
                    .map(Comfort::new)
                    .forEach(c -> this.comfortRepository.save(c));
        }
        if (this.roleRepository.count() == 0) {
            Arrays.stream(UserRoles.values())
                    .map(Role::new)
                    .forEach(r -> this.roleRepository.save(r));
        }
    }
}
