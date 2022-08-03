package bg.softuni.pochivka.web;

import bg.softuni.pochivka.model.dto.UserRegisterDTO;
import bg.softuni.pochivka.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserRegisterController {
    private UserService userService;

    @Autowired
    public UserRegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        if (!model.containsAttribute("userRegister")) {
            UserRegisterDTO userRegister = new UserRegisterDTO();
            model.addAttribute("userRegister", userRegister);
        }
        return "user-register";
    }

    @PostMapping("/register")
    public String postRegisterForm(@Valid @ModelAttribute UserRegisterDTO userRegister,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegister", userRegister);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegister",
                    bindingResult);
            return "redirect:/register";
        }

        boolean userByUsername = this.userService
                .findUserByUsername(userRegister.getUsername());
        if (userByUsername) {
            redirectAttributes.addFlashAttribute("userRegister", userRegister);
            redirectAttributes.addFlashAttribute("usernameExist", 1);
            return "redirect:/register";
        }
        boolean userByEmail = this.userService.findUserByEmail(userRegister.getEmail());
        if (userByEmail) {
            redirectAttributes.addFlashAttribute("userRegister", userRegister);
            redirectAttributes.addFlashAttribute("emailExist", 1);
            return "redirect:/register";
        }
        boolean passwordsMatch = this.userService
                .passwordsMatch(userRegister.getPassword(), userRegister.getConfirmPassword());
        if (!passwordsMatch) {
            redirectAttributes.addFlashAttribute("userRegister", userRegister);
            redirectAttributes.addFlashAttribute("passMatch", 0);
            return "redirect:/register";
        }

        this.userService.register(userRegister);
        return "redirect:/login";
    }
}
