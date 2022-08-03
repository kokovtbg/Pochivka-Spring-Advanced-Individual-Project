package bg.softuni.pochivka.web;

import bg.softuni.pochivka.model.dto.MessageDTO;
import bg.softuni.pochivka.model.dto.UserMessageDTO;
import bg.softuni.pochivka.service.MessageService;
import bg.softuni.pochivka.service.UserService;
import bg.softuni.pochivka.web.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MessageController {
    private final MessageService messageService;
    private final UserService userService;

    @Autowired
    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @GetMapping("/messages-input")
    public String loadMessagePage() {
        return "messages-input";
    }

    @ResponseBody
    @GetMapping("/messages-input-list")
    public ResponseEntity<List<MessageDTO>> getAllReceivedMessages(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(this.messageService.getAllReceivedMessages(userDetails.getUsername()));
    }

    @GetMapping("/messages-read/{id}")
    public String readMessage(Model model,
                              @PathVariable long id,
                              @AuthenticationPrincipal UserDetails userDetails) {
        MessageDTO messageById = this.messageService.getMessageById(id);
        if (messageById == null) {
            throw new ObjectNotFoundException(id, null, "Съобщение");
        }

        String receiverUsername = messageById.getReceiver().getUsername();
        String senderUsername = messageById.getSender().getUsername();
        if (!userDetails.getUsername().equals(receiverUsername) &&
                !userDetails.getUsername().equals(senderUsername)) {
            return "redirect:/";
        }

        model.addAttribute("id", id);
        return "messages-read-input";
    }

    @ResponseBody
    @GetMapping("/messages-read")
    public ResponseEntity<MessageDTO> readMessageRest(@RequestParam(name = "id") long id) {
        return ResponseEntity.ok(this.messageService.getMessageById(id));
    }

    @GetMapping("/messages-output")
    public String getOutputMessagesPage() {
        return "messages-output";
    }

    @ResponseBody
    @GetMapping("/messages-output-list")
    public ResponseEntity<List<MessageDTO>> getAllSentMessages(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(this.messageService.getAllSentMessages(userDetails.getUsername()));
    }

    @GetMapping("/message-send")
    public String getMessageSendPage(Model model) {
        model.addAttribute("sendMessage", new MessageDTO());
        return "messages-write";
    }

    @GetMapping("/message-send/{username}")
    public String getMessageSendPageWithUsername(Model model,
                                                 @PathVariable String username) {
        MessageDTO messageDTO = new MessageDTO();
        UserMessageDTO userMessageDTO = null;
        boolean userByUsernameExist = this.userService.findUserByUsername(username);
        if (userByUsernameExist) {
            userMessageDTO = new UserMessageDTO();
            userMessageDTO.setUsername(username);
        }
        if (userMessageDTO == null) {
            throw new ObjectNotFoundException(0, username, "Потребител");
        }
        messageDTO.setReceiver(userMessageDTO);
        messageDTO.setReceiverName(username);
        model.addAttribute("sendMessage", messageDTO);
        return "messages-write";
    }

    @PostMapping("/message-send")
    public String sendMessage(MessageDTO sendMessageDTO,
                              @AuthenticationPrincipal UserDetails userDetails) {
        long messageId = this.messageService.sendMessage(sendMessageDTO, userDetails.getUsername());

        return "redirect:/messages-input";
    }
}
