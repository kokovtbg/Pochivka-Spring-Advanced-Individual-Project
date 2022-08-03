package bg.softuni.pochivka.web;

import bg.softuni.pochivka.model.dto.UserRegisterDTO;
import bg.softuni.pochivka.model.user.PochivkaUserDetails;
import bg.softuni.pochivka.service.UserService;
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
public class UserEditController {
    private UserService userService;

    @Autowired
    public UserEditController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user-edit")
    public String getEditForm(@AuthenticationPrincipal UserDetails userDetails,
                              Model model) {
        if (!model.containsAttribute("userEdit")) {
            UserRegisterDTO userEdit = this.userService.getUserByUsername(userDetails.getUsername());
            model.addAttribute("userEdit", userEdit);
        }
        return "user-edit";
    }

    @PostMapping("/user-edit")
    public String postEditForm(@Valid @ModelAttribute("userEdit") UserRegisterDTO userEdit,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               @AuthenticationPrincipal PochivkaUserDetails userDetails) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userEdit", userEdit);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userEdit",
                    bindingResult);
            return "redirect:/user-edit";
        }

        boolean userByUsernameExist = this.userService
                .findUserByUsername(userEdit.getUsername());
        if (userByUsernameExist && !userDetails.getUsername().equals(userEdit.getUsername())) {
            redirectAttributes.addFlashAttribute("userEdit", userEdit);
            redirectAttributes.addFlashAttribute("usernameExist", 1);
            return "redirect:/user-edit";
        }
        UserRegisterDTO userByUsername = this.userService.getUserByUsername(userDetails.getUsername());
        boolean userByEmail = this.userService.findUserByEmail(userEdit.getEmail());
        if (userByEmail && !userByUsername.getEmail().equals(userEdit.getEmail())) {
            redirectAttributes.addFlashAttribute("userEdit", userEdit);
            redirectAttributes.addFlashAttribute("emailExist", 1);
            return "redirect:/user-edit";
        }
        boolean passwordsMatch = this.userService
                .passwordsMatch(userEdit.getPassword(), userEdit.getConfirmPassword());
        if (!passwordsMatch) {
            redirectAttributes.addFlashAttribute("userEdit", userEdit);
            redirectAttributes.addFlashAttribute("passMatch", 0);
            return "redirect:/user-edit";
        }

        this.userService.update(userEdit, userDetails);
        return "redirect:/user-profile";
    }
}
