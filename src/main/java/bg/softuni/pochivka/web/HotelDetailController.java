package bg.softuni.pochivka.web;

import bg.softuni.pochivka.model.dto.HotelViewDTO;
import bg.softuni.pochivka.model.entity.Hotel;
import bg.softuni.pochivka.service.HotelService;
import bg.softuni.pochivka.web.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HotelDetailController {
    private HotelService hotelService;

    @Autowired
    public HotelDetailController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/hotel-detail/{id}")
    public String hotelDetail(@PathVariable(name = "id") long id,
                              Model model) {

        HotelViewDTO hotelDTO = this.hotelService.findById(id);
        if (hotelDTO == null) {
            throw new ObjectNotFoundException(id, null, "Хотел");
        }
        model.addAttribute("hotelDetail", hotelDTO);
        return "hotel-detail";
    }
}
