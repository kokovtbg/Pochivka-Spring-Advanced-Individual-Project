package bg.softuni.pochivka.config;

import bg.softuni.pochivka.model.mapper.HotelMapper;
import bg.softuni.pochivka.model.mapper.UserMapper;
import bg.softuni.pochivka.repository.TownRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration
public class MapperConfig {
    private PasswordEncoder passwordEncoder;
    private TownRepository townRepository;

    @Autowired
    public MapperConfig(TownRepository townRepository) {
        this.passwordEncoder = new Pbkdf2PasswordEncoder();
        this.townRepository = townRepository;
    }

    @Bean
    public UserMapper userMapper() {
        return new UserMapper(this.passwordEncoder, this.townRepository);
    }

    @Bean
    public HotelMapper hotelMapper() {
        return new HotelMapper();
    }
}
