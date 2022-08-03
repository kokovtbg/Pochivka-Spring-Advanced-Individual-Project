package bg.softuni.pochivka.model.mapper;

import bg.softuni.pochivka.model.dto.UserRegisterDTO;
import bg.softuni.pochivka.model.entity.Town;
import bg.softuni.pochivka.model.entity.User;
import bg.softuni.pochivka.model.enums.TownEnum;
import bg.softuni.pochivka.repository.TownRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {
    private final PasswordEncoder passwordEncoder;
    private final TownRepository townRepository;

    @Autowired
    public UserMapper(PasswordEncoder passwordEncoder, TownRepository townRepository) {
        this.passwordEncoder = passwordEncoder;
        this.townRepository = townRepository;
    }

    public void userRegisterDTOToUser(UserRegisterDTO userRegisterDTO, User user) {
        user.setUsername(userRegisterDTO.getUsername());
        user.setEmail(userRegisterDTO.getEmail());
        user.setFirstName(userRegisterDTO.getFirstName());
        user.setLastName(userRegisterDTO.getLastName());
        String encodedPass = this.passwordEncoder.encode(userRegisterDTO.getPassword());
        user.setPassword(encodedPass);
        user.setTelephone(userRegisterDTO.getTelephone());
        String userRegisterDTOTown = userRegisterDTO.getTown();
        TownEnum townEnum = null;
        if (userRegisterDTOTown != null && !userRegisterDTOTown.equals("noTown")) {
            townEnum = TownEnum.valueOf(userRegisterDTOTown);
        }
        Town town = this.townRepository.findByName(townEnum);
        user.setTown(town);
    }
}
