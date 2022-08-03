package bg.softuni.pochivka.service;

import bg.softuni.pochivka.model.dto.MessageDTO;
import bg.softuni.pochivka.model.dto.UserMessageDTO;
import bg.softuni.pochivka.model.entity.Message;
import bg.softuni.pochivka.model.entity.User;
import bg.softuni.pochivka.repository.MessageRepository;
import bg.softuni.pochivka.repository.UserRepository;
import bg.softuni.pochivka.service.MessageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class MessageServiceTests {
    private MessageRepository mockedMessageRepository;
    private UserRepository mockedUserRepository;
    private MessageService messageService;

    @BeforeEach
    public void init() {
        this.mockedMessageRepository = Mockito.mock(MessageRepository.class);
        this.mockedUserRepository = Mockito.mock(UserRepository.class);
        this.messageService = new MessageService(mockedMessageRepository, mockedUserRepository);
    }

    @Test
    public void getAllReceivedMessages() {
        String receiverUsername = "petar";
        List<Message> messages = new ArrayList<>();
        Message message = new Message();
        makeMessage(message);
        messages.add(message);
        Mockito.when(this.mockedMessageRepository.findAllByReceiverUsername(receiverUsername))
                .thenReturn(messages);
        String actual = this.messageService.getAllReceivedMessages(receiverUsername).get(0).getContent();
        String expected = this.mockedMessageRepository.findAllByReceiverUsername(receiverUsername).get(0).getContent();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getMessageById() {
        long id = 1;
        Message message = new Message();
        makeMessage(message);
        Mockito.when(this.mockedMessageRepository.findById(id))
                .thenReturn(Optional.of(message));
        String actual = this.messageService.getMessageById(id).getContent();
        String expected = null;
        Optional<Message> optMessById = this.mockedMessageRepository.findById(id);
        Message messageInRepo;
        if (optMessById.isPresent()) {
            messageInRepo = optMessById.get();
            expected = messageInRepo.getContent();
        }
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getAllSentMessages() {
        String senderUsername = "maria";
        List<Message> messages = new ArrayList<>();
        Message message = new Message();
        makeMessage(message);
        messages.add(message);
        Mockito.when(this.mockedMessageRepository.findAllBySenderUsername(senderUsername))
                .thenReturn(messages);
        String actual = this.messageService.getAllSentMessages(senderUsername).get(0).getContent();
        String expected = this.mockedMessageRepository.findAllBySenderUsername(senderUsername).get(0).getContent();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void sendMessage() {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setReceiverName("maria");
        Message message = new Message();
        message.setContent("Some content");
        message.setDateTime(LocalDateTime.now());
        String senderUsername = "petar";
        User sender = new User();
        sender.setUsername("petar");
        Mockito.when(this.mockedUserRepository.findByUsername(senderUsername))
                .thenReturn(Optional.of(sender));
        Optional<User> optSenderByUsername = this.mockedUserRepository.findByUsername(senderUsername);
        User senderInRepo = null;
        if (optSenderByUsername.isPresent()) {
            senderInRepo = optSenderByUsername.get();
        }
        message.setSender(senderInRepo);
        User receiver = new User();
        receiver.setUsername("maria");
        Mockito.when(this.mockedUserRepository.findByUsername(receiver.getUsername()))
                .thenReturn(Optional.of(receiver));
        Optional<User> optReceiverByUsername = this.mockedUserRepository.findByUsername(receiver.getUsername());
        User receiverInRepo = null;
        if (optReceiverByUsername.isPresent()) {
            receiverInRepo = optReceiverByUsername.get();
        }
        message.setReceiver(receiverInRepo);
        Mockito.when(this.mockedMessageRepository.save(message))
                .thenReturn(message);
        Mockito.when(this.mockedMessageRepository.findById(1L))
                .thenReturn(Optional.of(message));
        long actual = this.messageService.sendMessage(messageDTO, messageDTO.getReceiverName());
        long expected = this.mockedMessageRepository.findById(1L).get().getId();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGetAllMessagesToAdmin() {
        Message message = new Message();
        makeMessage(message);
        List<Message> messages = new ArrayList<>();
        messages.add(message);
        Mockito.when(this.mockedMessageRepository.findAllBySenderIsNullAndReceiverIsNull())
                .thenReturn(messages);
        String expectedContent = this.mockedMessageRepository.findAllBySenderIsNullAndReceiverIsNull().get(0).getContent();
        String actualContent = this.messageService.getAllMessagesToAdmin().get(0).getContent();
        Assertions.assertEquals(expectedContent, actualContent);
    }

    private void makeMessage(Message message) {
        message.setId(1);
        message.setContent("Some content");
        message.setDateTime(LocalDateTime.now());
        User receiver = new User();
        receiver.setUsername("petar");
        receiver.setFirstName("Petar");
        receiver.setLastName("Petrov");
        message.setReceiver(receiver);
        User sender = new User();
        sender.setUsername("maria");
        sender.setFirstName("Maria");
        sender.setLastName("Ivanov");
        message.setSender(sender);
    }
}
