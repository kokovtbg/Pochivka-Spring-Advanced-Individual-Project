package bg.softuni.pochivka.service;

import bg.softuni.pochivka.model.dto.UserRegisterDTO;
import bg.softuni.pochivka.model.entity.Hotel;
import bg.softuni.pochivka.model.entity.Region;
import bg.softuni.pochivka.model.entity.Town;
import bg.softuni.pochivka.model.entity.User;
import bg.softuni.pochivka.model.enums.Accommodation;
import bg.softuni.pochivka.model.enums.RegionEnum;
import bg.softuni.pochivka.model.enums.TownEnum;
import bg.softuni.pochivka.model.mapper.UserMapper;
import bg.softuni.pochivka.model.user.PochivkaUserDetails;
import bg.softuni.pochivka.repository.HotelRepository;
import bg.softuni.pochivka.repository.TownRepository;
import bg.softuni.pochivka.repository.UserRepository;
import bg.softuni.pochivka.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UserServiceTests {
    private User testUser;
    private UserRepository mockedUserRepository;
    private TownRepository mockedTownRepository;
    private HotelRepository mockedHotelRepository;
    private UserService userService;
    private PochivkaUserDetails pochivkaUserDetails;
    private UserMapper userMapper;

    @BeforeEach
    public void init() {
        this.testUser = new User();
        this.testUser.setId(1);
        this.testUser.setUsername("petar");
        this.testUser.setEmail("petar@abv.bg");
        this.testUser.setPassword("123");
        this.testUser.setFirstName("Petar");
        this.testUser.setLastName("Petrov");
        this.testUser.setTelephone("888");
        this.pochivkaUserDetails = new PochivkaUserDetails("petar", "123",
                "Petar", "Petrov", new ArrayList<>());

        this.mockedUserRepository = Mockito.mock(UserRepository.class);

        this.mockedTownRepository = Mockito.mock(TownRepository.class);
        Town testTown = new Town();
        testTown.setId(1);
        testTown.setName(TownEnum.VELIKO_TARNOVO);
        testTown.setRegion(new Region(RegionEnum.MOUNTAIN));
        this.mockedTownRepository.save(testTown);
        this.testUser.setTown(testTown);

        this.mockedHotelRepository = Mockito.mock(HotelRepository.class);
        List<Hotel> testHotels = new ArrayList<>();
        Hotel testHotel = new Hotel();
        testHotel.setId(1);
        testHotel.setName("Hotel Neva");
        testHotel.setInformation("Some information");
        testHotel.setAccommodation(Accommodation.ALL_INCLUSIVE);
        testHotel.setImages(List.of("www.picture.com"));
        testHotel.setOwner(this.testUser);
        testHotel.setTown(testTown);
        testHotels.add(testHotel);
        testHotels.forEach(h -> mockedHotelRepository.save(h));
        this.testUser.setHotels(testHotels);

        this.userMapper = new UserMapper(new Pbkdf2PasswordEncoder(), this.mockedTownRepository);
        this.userService = new UserService(mockedUserRepository, this.userMapper);
    }

    @Test
    public void userService_findUserByUsername_ShouldReturnTrue() {
        Mockito.when(this.mockedUserRepository.findByUsername("petar"))
                .thenReturn(Optional.of(this.testUser));
        boolean expected = true;
        boolean actual = userService.findUserByUsername("petar");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void userService_findUserByUsername_ShouldReturnFalse() {
        Mockito.when(this.mockedUserRepository.findByUsername("petar"))
                .thenReturn(Optional.of(this.testUser));
        boolean expected = false;
        boolean actual = userService.findUserByUsername("maria");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void userService_findUserByEmail_ShouldReturnTrue() {
        Mockito.when(this.mockedUserRepository.findByEmail("petar@abv.bg"))
                .thenReturn(Optional.of(this.testUser));
        boolean expected = true;
        boolean actual = userService.findUserByEmail("petar@abv.bg");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void userService_findUserByEmail_ShouldReturnFalse() {
        Mockito.when(this.mockedUserRepository.findByUsername("petar"))
                .thenReturn(Optional.of(this.testUser));
        boolean expected = false;
        boolean actual = userService.findUserByEmail("petar@gmail.com");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void userService_passwordsMatch_ShouldReturnTrue() {
        Mockito.when(this.mockedUserRepository.findByUsername("petar"))
                .thenReturn(Optional.of(this.testUser));
        boolean expected = true;
        boolean actual = userService.passwordsMatch(this.testUser.getPassword(), "123");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void userService_passwordsMatch_ShouldReturnFalse() {
        Mockito.when(this.mockedUserRepository.findByUsername("petar"))
                .thenReturn(Optional.of(this.testUser));
        boolean expected = false;
        boolean actual = userService.passwordsMatch(this.testUser.getPassword(), "1233");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void userService_register_ShouldSaveUser() {

        User user = new User();
        user.setUsername("maria");
        user.setEmail("maria@abv.bg");
        user.setPassword("123");
        user.setFirstName("Maria");
        user.setLastName("Petrov");
        user.setTelephone("345");
        UserRegisterDTO userDTO = new UserRegisterDTO();
        userDTO.setUsername("maria");
        userDTO.setEmail("maria@abv.bg");
        userDTO.setPassword("123");
        userDTO.setFirstName("Maria");
        userDTO.setLastName("Petrov");
        userDTO.setTelephone("345");
        Mockito.when(this.mockedUserRepository.save(user))
                .thenReturn(user);
        Mockito.when(this.mockedUserRepository.findByUsername(user.getUsername()))
                .thenReturn(Optional.of(user));
        UserRegisterDTO actual = this.userService.register(userDTO);

        Assertions.assertEquals(actual.getUsername(), this.userService.getUserByUsername("maria").getUsername());
        Assertions.assertEquals(actual.getEmail(), this.userService.getUserByUsername("maria").getEmail());
    }

    @Test
    public void userService_getUserByUsername_ShouldReturnCorrect() {
        Mockito.when(this.mockedUserRepository.findByUsername("petar"))
                .thenReturn(Optional.of(this.testUser));
        UserRegisterDTO petarDTO = this.userService.getUserByUsername("petar");
        Assertions.assertEquals("petar", petarDTO.getUsername());
        Assertions.assertEquals("petar@abv.bg", petarDTO.getEmail());
        Assertions.assertEquals("123", petarDTO.getPassword());
        Assertions.assertEquals("888", petarDTO.getTelephone());
        Assertions.assertEquals("Petar", petarDTO.getFirstName());
        Assertions.assertEquals("Petrov", petarDTO.getLastName());
    }

    @Test
    public void userService_update_ShouldUpdate() {
        Mockito.when(this.mockedUserRepository.findByUsername("petar"))
                .thenReturn(Optional.of(this.testUser));
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("maria");
        userRegisterDTO.setEmail("maria@abv.bg");
        userRegisterDTO.setPassword("123");
        userRegisterDTO.setConfirmPassword("123");
        userRegisterDTO.setFirstName("Maria");
        userRegisterDTO.setLastName("Petrov");
        userRegisterDTO.setTelephone("345");
        User user = null;
        Optional<User> optPetar = this.mockedUserRepository.findByUsername("petar");
        if (optPetar.isPresent()) {
            user = optPetar.get();
            user.setUsername("maria");
            user.setEmail("maria@abv.bg");
            user.setPassword("123");
            user.setFirstName("Maria");
            user.setLastName("Petrov");
            user.setTelephone("345");
        }

        if (user != null) {
            Mockito.when(this.mockedUserRepository.save(user))
                    .thenReturn(user);
            Mockito.when(this.mockedUserRepository.findByUsername(user.getUsername()))
                    .thenReturn(Optional.of(user));
        }
        UserRegisterDTO updatedUser = this.userService.update(userRegisterDTO, this.pochivkaUserDetails);
        Assertions.assertEquals(updatedUser.getUsername(), this.userService.getUserByUsername("maria").getUsername());
        Assertions.assertEquals(updatedUser.getEmail(), this.userService.getUserByUsername("maria").getEmail());
    }
}
