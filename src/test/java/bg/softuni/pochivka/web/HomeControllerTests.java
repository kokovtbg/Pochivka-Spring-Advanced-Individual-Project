package bg.softuni.pochivka.web;

import bg.softuni.pochivka.model.dto.HotelSearchDTO;
import bg.softuni.pochivka.model.dto.HotelViewDTO;
import bg.softuni.pochivka.model.entity.*;
import bg.softuni.pochivka.model.enums.*;
import bg.softuni.pochivka.repository.*;
import bg.softuni.pochivka.service.HotelService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private HotelRepository hotelRepository;
    @MockBean
    private ComfortRepository comfortRepository;
    @MockBean
    private TownRepository townRepository;
    @MockBean
    private RegionRepository regionRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private HotelService hotelService;

    @BeforeEach
    void init() {
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
    }

    @AfterEach
    void tearDown() {
        this.hotelRepository.deleteAll();
        this.comfortRepository.deleteAll();
        this.townRepository.deleteAll();
        this.regionRepository.deleteAll();
        this.userRepository.deleteAll();
    }

    @Test
    public void getHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/home"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("home"));
    }

    @Test
    public void postSearch() throws Exception {
        HotelSearchDTO hotelSearchDTO = new HotelSearchDTO();
        hotelSearchDTO.setName("");
        hotelSearchDTO.setTownName("noTown");
        hotelSearchDTO.setSort("noSort");
        List<HotelViewDTO> allByNameAndAccommodationAndCategoryAndComfort = this.hotelService
                .getAllByNameAndAccommodationAndCategoryAndComfort(hotelSearchDTO);
        mockMvc.perform(MockMvcRequestBuilders.get("/search"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("hotelList", allByNameAndAccommodationAndCategoryAndComfort))
                .andExpect(MockMvcResultMatchers.view().name("home"));
    }
}
