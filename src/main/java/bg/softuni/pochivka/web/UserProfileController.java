package bg.softuni.pochivka.web;

import bg.softuni.pochivka.model.dto.UserRegisterDTO;
import bg.softuni.pochivka.model.user.PochivkaUserDetails;
import bg.softuni.pochivka.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserProfileController {
    private UserService userService;

    @Autowired
    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user-profile")
    public String getUserProfile(@AuthenticationPrincipal PochivkaUserDetails userDetails,
                                 Model model) {
        UserRegisterDTO userDTO = this.userService.getUserByUsername(userDetails.getUsername());
        model.addAttribute("userProfile",  userDTO);
        return "user-profile";
    }
}
