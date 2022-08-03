package bg.softuni.pochivka.web;

import bg.softuni.pochivka.model.dto.HotelSearchDTO;
import bg.softuni.pochivka.model.dto.HotelViewDTO;
import bg.softuni.pochivka.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class IndexController {
    private HotelService hotelService;

    @Autowired
    public IndexController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/search-sea")
    public String getHotelBySea(@ModelAttribute("hotelDTO") HotelSearchDTO hotelDTO,
                                Model model) {
        List<HotelViewDTO> hotelViewDTOs = this.hotelService.getAllBySea();
        model.addAttribute("hotelList", hotelViewDTOs);
        return "home";
    }

    @GetMapping("/search-mountain")
    public String getHotelByMountain(@ModelAttribute("hotelDTO") HotelSearchDTO hotelDTO,
                                     Model model) {
        List<HotelViewDTO> hotelViewDTOs = this.hotelService.getAllByMountain();
        model.addAttribute("hotelList", hotelViewDTOs);
        return "home";
    }

    @GetMapping("/search-ai")
    public String getHotelByAllInclusive(@ModelAttribute("hotelDTO") HotelSearchDTO hotelDTO,
                                         Model model) {
        List<HotelViewDTO> hotelViewDTOs = this.hotelService.getAllByAllInclusive();
        model.addAttribute("hotelList", hotelViewDTOs);
        return "home";
    }
}
