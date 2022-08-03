package bg.softuni.pochivka.web;

import bg.softuni.pochivka.model.dto.HotelAddDTO;
import bg.softuni.pochivka.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class HotelAddController {
    private final HotelService hotelService;

    @Autowired
    public HotelAddController(HotelService hotelService) {
        this.hotelService = hotelService;
    }


    @GetMapping("/hotel-add")
    public String getHotelAddFrom(Model model) {
        if (!model.containsAttribute("hotelAdd")) {
            model.addAttribute("hotelAdd", new HotelAddDTO());
        }
        return "hotel-add";
    }

    @PostMapping("/hotel-add")
    public String postHotelAddForm(@Valid @ModelAttribute HotelAddDTO hotelAdd,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes,
                                   @AuthenticationPrincipal UserDetails userDetails) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("hotelAdd", hotelAdd);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.hotelAdd",
                    bindingResult);
            return "redirect:/hotel-add";
        }

        if (!this.hotelService.hasRoomsAndAllPricesGreaterThanZero(hotelAdd)) {
            redirectAttributes.addFlashAttribute("hotelAdd", hotelAdd);
            redirectAttributes.addFlashAttribute("wrongPrice", 1);
            return "redirect:/hotel-add";
        }
        this.hotelService.register(hotelAdd, userDetails.getUsername());

        return "redirect:/home";

    }
}
