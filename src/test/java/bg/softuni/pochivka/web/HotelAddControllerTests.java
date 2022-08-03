package bg.softuni.pochivka.web;

import bg.softuni.pochivka.model.dto.HotelAddDTO;
import bg.softuni.pochivka.model.enums.*;
import bg.softuni.pochivka.repository.UserRepository;
import bg.softuni.pochivka.service.HotelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HotelAddControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private HotelService hotelService;
    @MockBean(name = "petar")
    private UserDetails userDetails;
    @MockBean
    private UserRepository userRepository;

    @Test
    @WithMockUser("petar")
    public void getHotelAddForm() throws Exception {
        mockMvc.perform(get("/hotel-add"))
                .andExpect(status().isOk())
                .andExpect(view().name("hotel-add"));
    }

    @Test
    @WithMockCustomUser(username = "petar", password = "12345", firstName = "Petar", lastName = "Petrov")
    public void postHotelAddForm() throws Exception {
        Mockito.when(this.hotelService.hasRoomsAndAllPricesGreaterThanZero(Mockito.any(HotelAddDTO.class)))
                .thenReturn(true);

        mockMvc.perform(post("/hotel-add")
                        .param("name", "Hotel Kremikovci")
                        .param("website", "www.kremikovci.com")
                        .param("information", "Some information that must be long")
                        .param("images", "www.images.com")
                        .param("accommodation", Accommodation.ALL_INCLUSIVE.toString())
                        .param("category", HotelCategory.FIVE_STAR.toString())
                        .param("comforts", String.join("", List.of(ComfortEnum.BARBEQUE.toString())))
                        .param("town", TownEnum.KITEN.toString())
                        .param("rooms.price", "20")
                        .param("rooms.roomType", "RoomType.SINGLE")
                        .param("rooms.season", "Season.STRONG_WINTER")
                        .with(csrf())
                        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    @WithMockCustomUser(username = "petar", password = "12345", firstName = "Petar", lastName = "Petrov")
    public void testHotelAddForm_BindingResultHasErrors() throws Exception {
        Mockito.when(this.hotelService.hasRoomsAndAllPricesGreaterThanZero(Mockito.any(HotelAddDTO.class)))
                .thenReturn(true);

        mockMvc.perform(post("/hotel-add")
                        .param("name", "Hotel Kremikovci")
                        .param("website", "www.kremikovci.com")
                        .param("information", "Some info")
                        .param("images", "www.images.com")
                        .param("accommodation", Accommodation.ALL_INCLUSIVE.toString())
                        .param("category", HotelCategory.FIVE_STAR.toString())
                        .param("comforts", String.join("", List.of(ComfortEnum.BARBEQUE.toString())))
                        .param("town", TownEnum.KITEN.toString())
                        .param("rooms.price", "20")
                        .param("rooms.roomType", "RoomType.SINGLE")
                        .param("rooms.season", "Season.STRONG_WINTER")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/hotel-add"));
    }

    @Test
    @WithMockCustomUser(username = "petar", password = "12345", firstName = "Petar", lastName = "Petrov")
    public void testHotelAddForm_NoRooms() throws Exception {
        Mockito.when(this.hotelService.hasRoomsAndAllPricesGreaterThanZero(Mockito.any(HotelAddDTO.class)))
                .thenReturn(false);

        mockMvc.perform(post("/hotel-add")
                        .param("name", "Hotel Kremikovci")
                        .param("website", "www.kremikovci.com")
                        .param("information", "Some information that must be long")
                        .param("images", "www.images.com")
                        .param("accommodation", Accommodation.ALL_INCLUSIVE.toString())
                        .param("category", HotelCategory.FIVE_STAR.toString())
                        .param("comforts", String.join("", List.of(ComfortEnum.BARBEQUE.toString())))
                        .param("town", TownEnum.KITEN.toString())
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/hotel-add"));
    }
}
