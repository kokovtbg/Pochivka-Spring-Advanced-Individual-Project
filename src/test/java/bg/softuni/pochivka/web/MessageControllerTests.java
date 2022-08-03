package bg.softuni.pochivka.web;

import bg.softuni.pochivka.model.dto.MessageDTO;
import bg.softuni.pochivka.model.dto.UserMessageDTO;
import bg.softuni.pochivka.service.MessageService;
import bg.softuni.pochivka.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MessageControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MessageService messageService;
    @MockBean
    private UserService userService;

    @Test
    @WithMockUser
    void testLoadMessagePage() throws Exception {
        mockMvc.perform(get("/messages-input"))
                .andExpect(view().name("messages-input"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "petar")
    void testGetAllReceivedMessages() throws Exception {
        MessageDTO messageDTO = new MessageDTO();
        setMessageDTO(messageDTO);
        Mockito.when(this.messageService.getAllReceivedMessages("petar"))
                .thenReturn(List.of(messageDTO));
        mockMvc.perform(get("/messages-input-list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].content", is("Some content")))
                .andExpect(jsonPath("$.[0].sender.username", is("maria")))
                .andExpect(jsonPath("$.[0].receiver.username", is("petar")));
    }

    @Test
    @WithMockUser(username = "petar")
    void testReadMessage() throws Exception {
        MessageDTO messageDTO = new MessageDTO();
        setMessageDTO(messageDTO);
        Mockito.when(this.messageService.getMessageById(1))
                .thenReturn(messageDTO);
        mockMvc.perform(get("/messages-read/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("messages-read-input"));
    }

    @Test
    @WithMockUser
    void testReadMessageThrowsWhenNoObjectFound() throws Exception {
        Mockito.when(this.messageService.getMessageById(1))
                .thenReturn(null);
        mockMvc.perform(get("/messages-read/{id}", 1))
                .andExpect(status().is4xxClientError())
                .andExpect(view().name("object-not-found"));
    }

    @Test
    @WithMockUser(username = "koko")
    void testReadMessageRedirects() throws Exception {
        MessageDTO messageDTO = new MessageDTO();
        setMessageDTO(messageDTO);
        Mockito.when(this.messageService.getMessageById(1))
                .thenReturn(messageDTO);
        mockMvc.perform(get("/messages-read/{id}", 1))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    void testReadMessageRest() throws Exception {
        MessageDTO messageDTO = new MessageDTO();
        setMessageDTO(messageDTO);
        Mockito.when(this.messageService.getMessageById(1))
                        .thenReturn(messageDTO);
        mockMvc.perform(get("/messages-read?id=1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testGetOutputMessagesPage() throws Exception {
        mockMvc.perform(get("/messages-output"))
                .andExpect(status().isOk())
                .andExpect(view().name("messages-output"));
    }

    @Test
    @WithMockUser(username = "maria")
    void testGetAllSentMessages() throws Exception {
        MessageDTO messageDTO = new MessageDTO();
        setMessageDTO(messageDTO);
        Mockito.when(this.messageService.getAllSentMessages("maria"))
                .thenReturn(List.of(messageDTO));
        mockMvc.perform(get("/messages-output-list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].content", is("Some content")))
                .andExpect(jsonPath("$.[0].sender.username", is("maria")))
                .andExpect(jsonPath("$.[0].receiver.username", is("petar")));
    }

    @Test
    @WithMockUser
    void testGetMessageSendPage() throws Exception {
        mockMvc.perform(get("/message-send"))
                .andExpect(status().isOk())
                .andExpect(view().name("messages-write"));
    }

    @Test
    @WithMockUser
    void testGetMessageSendPageWithUsername() throws Exception {
        Mockito.when(this.userService.findUserByUsername("maria"))
                        .thenReturn(true);
        mockMvc.perform(get("/message-send/{username}", "maria"))
                .andExpect(status().isOk())
                .andExpect(view().name("messages-write"));
    }

    @Test
    @WithMockUser
    void testGetMessageSendPageWithUsernameShouldThrow() throws Exception {
        Mockito.when(this.userService.findUserByUsername("maria"))
                .thenReturn(false);
        mockMvc.perform(get("/message-send/{username}", "maria"))
                .andExpect(status().is4xxClientError())
                .andExpect(view().name("object-not-found"));
    }

    @Test
    @WithMockUser(username = "maria")
    void testSendMessage() throws Exception {
        MessageDTO messageDTO = new MessageDTO();
        setMessageDTO(messageDTO);
        mockMvc.perform(post("/message-send").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/messages-input"));
    }

    private void setMessageDTO(MessageDTO messageDTO) {
        messageDTO.setContent("Some content");
        UserMessageDTO sender = new UserMessageDTO();
        sender.setUsername("maria");
        sender.setFirstName("Maria");
        sender.setLastName("Petrova");
        messageDTO.setSender(sender);
        UserMessageDTO receiver = new UserMessageDTO();
        receiver.setUsername("petar");
        receiver.setFirstName("Petar");
        receiver.setLastName("Petrov");
        messageDTO.setReceiver(receiver);
    }
}
