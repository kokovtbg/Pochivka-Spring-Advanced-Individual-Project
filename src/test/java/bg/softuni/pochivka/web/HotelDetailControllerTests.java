package bg.softuni.pochivka.web;

import bg.softuni.pochivka.model.dto.HotelViewDTO;
import bg.softuni.pochivka.model.entity.*;
import bg.softuni.pochivka.model.enums.*;
import bg.softuni.pochivka.model.mapper.HotelMapper;
import bg.softuni.pochivka.repository.*;
import bg.softuni.pochivka.service.HotelService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HotelDetailControllerTests {

    @Autowired
    private MockMvc mockMvc;
    private HotelService hotelService;
    @MockBean
    private HotelRepository hotelRepository;
    @MockBean
    private TownRepository townRepository;
    @MockBean
    private ComfortRepository comfortRepository;
    @MockBean
    private RegionRepository regionRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoomRepository roomRepository;
    private HotelMapper hotelMapper;

    @BeforeEach
    void init() {
        this.hotelService = new HotelService(hotelRepository, comfortRepository,
                townRepository, roomRepository, userRepository, hotelMapper);

    }

    @AfterEach
    void tearDown() {
        this.comfortRepository.deleteAll();
        this.hotelRepository.deleteAll();
        this.userRepository.deleteAll();
        this.townRepository.deleteAll();
        this.regionRepository.deleteAll();
    }

    @Test
    void testHotelDetail() throws Exception {
        Hotel hotel = new Hotel();
        hotel.setName("Hotel Kremikovci");
        hotel.setWebsite("www.kremikovci.com");
        hotel.setInformation("Some information");
        hotel.setCategory(HotelCategory.FIVE_STAR);
        hotel.setAccommodation(Accommodation.ALL_INCLUSIVE);
        hotel.setImages(List.of("www.photo.com"));
        Arrays.stream(ComfortEnum.values()).forEach(c -> {
            Comfort comfort = new Comfort(c);
            this.comfortRepository.save(comfort);
        });
        hotel.setComforts(this.comfortRepository.findAll());
        Town town = new Town();
        town.setName(TownEnum.KITEN);
        Region region = new Region(RegionEnum.SEA);
        this.regionRepository.save(region);
        town.setRegion(region);
        this.townRepository.save(town);
        hotel.setTown(town);
        User user = new User();
        user.setUsername("petar");
        user.setEmail("petar@abv.bg");
        user.setFirstName("Petar");
        user.setLastName("Petrov");
        user.setTelephone("123");
        user.setPassword("12345");
        this.userRepository.save(user);
        hotel.setOwner(user);
        Mockito.when(this.hotelRepository.save(hotel))
                .thenReturn(hotel);
        this.hotelRepository.save(hotel);
        Mockito.when(this.hotelRepository.findById(1L))
                .thenReturn(Optional.of(hotel));

        mockMvc.perform(get("/hotel-detail/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("hotel-detail"));
    }

    @Test
    void testHotelDetailShouldThrow() throws Exception {
        Hotel hotel = new Hotel();
        hotel.setName("Hotel Kremikovci");
        hotel.setWebsite("www.kremikovci.com");
        hotel.setInformation("Some information");
        hotel.setCategory(HotelCategory.FIVE_STAR);
        hotel.setAccommodation(Accommodation.ALL_INCLUSIVE);
        hotel.setImages(List.of("www.photo.com"));
        Arrays.stream(ComfortEnum.values()).forEach(c -> {
            Comfort comfort = new Comfort(c);
            this.comfortRepository.save(comfort);
        });
        hotel.setComforts(this.comfortRepository.findAll());
        Town town = new Town();
        town.setName(TownEnum.KITEN);
        Region region = new Region(RegionEnum.SEA);
        this.regionRepository.save(region);
        town.setRegion(region);
        this.townRepository.save(town);
        hotel.setTown(town);
        User user = new User();
        user.setUsername("petar");
        user.setEmail("petar@abv.bg");
        user.setFirstName("Petar");
        user.setLastName("Petrov");
        user.setTelephone("123");
        user.setPassword("12345");
        this.userRepository.save(user);
        hotel.setOwner(user);
        Mockito.when(this.hotelRepository.save(hotel))
                .thenReturn(hotel);
        this.hotelRepository.save(hotel);
        Mockito.when(this.hotelRepository.findById(1L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/hotel-detail/{id}", 1))
                .andExpect(status().is4xxClientError())
                .andExpect(view().name("object-not-found"));
    }
}
