package bg.softuni.pochivka.web;

import bg.softuni.pochivka.model.dto.MessageDTO;
import bg.softuni.pochivka.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminPageController {
    private MessageService messageService;

    @Autowired
    public AdminPageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/pages/admins")
    public String getAllMessages(Model model) {
        List<MessageDTO> allMessagesToAdmin = messageService.getAllMessagesToAdmin();
        model.addAttribute("messages", allMessagesToAdmin);
        return "admin-messages";
    }

    @GetMapping("/pages/admins/read/{id}")
    public String readMessage(@PathVariable("id") long id,
                              Model model) {
        MessageDTO messageById = this.messageService.getMessageById(id);
        model.addAttribute("message", messageById);
        return "admin-read-message";
    }

    @RequestMapping(value = "/pages/admins/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteMessages() {
        this.messageService.deleteAllFromAdmin();
        return "redirect:/pages/admins";
    }
}
