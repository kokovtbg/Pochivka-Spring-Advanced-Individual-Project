package bg.softuni.pochivka.web;

import bg.softuni.pochivka.service.HotelService;
import bg.softuni.pochivka.service.UserService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class TestConfiguration {
    @Bean
    @Primary
    public UserService mockedUserService() {
        return Mockito.mock(UserService.class);
    }

    @Bean
    @Primary
    public HotelService mockedHotelService() {
        return Mockito.mock(HotelService.class);
    }
}
