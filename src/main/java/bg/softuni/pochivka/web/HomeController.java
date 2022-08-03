package bg.softuni.pochivka.web;

import bg.softuni.pochivka.model.dto.HotelSearchDTO;
import bg.softuni.pochivka.model.dto.HotelViewDTO;
import bg.softuni.pochivka.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HomeController {
    private final HotelService hotelService;

    @Autowired
    public HomeController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/home")
    public String getHome(Model model) {
        HotelSearchDTO hotelDTO = new HotelSearchDTO();
        if (!model.containsAttribute("hotelDTO")) {
            model.addAttribute("hotelDTO", hotelDTO);
        }
        return "home";
    }

    @GetMapping("/search")
    public String postSearch(@ModelAttribute("hotelDTO") HotelSearchDTO hotelDTO,
                             Model model) {

        List<HotelViewDTO> hotelList = this.hotelService
                .getAllByNameAndAccommodationAndCategoryAndComfort(hotelDTO);
        model.addAttribute("hotelList", hotelList);
        return "home";
    }
}
