package bg.softuni.pochivka.service;

import bg.softuni.pochivka.model.dto.MessageDTO;
import bg.softuni.pochivka.model.dto.UserMessageDTO;
import bg.softuni.pochivka.model.entity.Message;
import bg.softuni.pochivka.model.entity.User;
import bg.softuni.pochivka.model.enums.UserRoles;
import bg.softuni.pochivka.repository.MessageRepository;
import bg.softuni.pochivka.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private MessageRepository messageRepository;
    private UserRepository userRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public List<MessageDTO> getAllReceivedMessages(String username) {
        List<Message> messages = this.messageRepository.findAllByReceiverUsername(username);
        List<MessageDTO> messageDTOs = new ArrayList<>();
        messages.forEach(m -> {
            MessageDTO messageDTO = getMessageDTOFromMessage(m);
            messageDTOs.add(messageDTO);
        });

        return messageDTOs;
    }

    public MessageDTO getMessageById(long id) {
        Optional<Message> optMessById = this.messageRepository.findById(id);
        Message message;
        MessageDTO messageDTO = null;
        if (optMessById.isPresent()) {
            message = optMessById.get();
            messageDTO = getMessageDTOFromMessage(message);
        }
        return messageDTO;
    }

    public List<MessageDTO> getAllSentMessages(String username) {
        List<Message> messages = this.messageRepository.findAllBySenderUsername(username);
        List<MessageDTO> messageDTOs = new ArrayList<>();
        messages.forEach(m -> {
            MessageDTO messageDTO = getMessageDTOFromMessage(m);
            messageDTOs.add(messageDTO);
        });
        return messageDTOs;
    }

    public long sendMessage(MessageDTO messageDTO, String username) {
        Message message = new Message();
        message.setContent(messageDTO.getContent());
        message.setDateTime(LocalDateTime.now());
        User sender = null;
        Optional<User> optSenderByUsername = this.userRepository.findByUsername(username);
        if (optSenderByUsername.isPresent()) {
            sender = optSenderByUsername.get();
        }
        message.setSender(sender);
        User receiver = null;
        Optional<User> optReceiverByUsername = this.userRepository.findByUsername(messageDTO.getReceiverName());
        if (optReceiverByUsername.isPresent()) {
            receiver = optReceiverByUsername.get();
        }
        message.setReceiver(receiver);
        this.messageRepository.save(message);
        return message.getId();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void sendAllMessagesForTheDay() {
        List<MessageDTO> messageDTOs = new ArrayList<>();
        LocalDateTime localDateTime = LocalDateTime.now();
        List<Message> allByDateTimeBetween = this.messageRepository.findAllByDateTimeBetween(localDateTime.minusDays(1), LocalDateTime.now());
        allByDateTimeBetween.forEach(m -> {
            MessageDTO messageDTO = getMessageDTOFromMessage(m);
            messageDTOs.add(messageDTO);
        });
        messageDTOs.forEach(this::sendMessageToAdmin);
    }

    public List<MessageDTO> getAllMessagesToAdmin() {
        List<Message> messages = this.messageRepository.findAllBySenderIsNullAndReceiverIsNull();
        List<MessageDTO> messageDTOs = new ArrayList<>();
        messages.forEach(m -> {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setId(m.getId());
            messageDTO.setContent(m.getContent());
            messageDTO.setDateTime(m.getDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm:ss")));
            messageDTOs.add(messageDTO);
        });
        return messageDTOs;
    }

    @Transactional
    public void deleteAllFromAdmin() {
        this.messageRepository.deleteAllBySenderIsNullAndReceiverIsNull();
    }

    private void sendMessageToAdmin(MessageDTO messageDTO) {
        MessageDTO messageDTOToAdmin = new MessageDTO();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Изпращач - ").append(messageDTO.getSender().getUsername()).append("(")
                .append(messageDTO.getSender().getFirstName()).append(" ")
                .append(messageDTO.getSender().getLastName()).append(")").append("\n");
        stringBuilder.append("Получател - ").append(messageDTO.getReceiver().getUsername())
                .append("(").append(messageDTO.getReceiver().getFirstName())
                .append(" ").append(messageDTO.getReceiver().getLastName()).append(")").append("\n");
        stringBuilder.append("Съдържание: " + "\n").append(messageDTO.getContent());
        messageDTOToAdmin.setContent(stringBuilder.toString());
        messageDTOToAdmin.setDateTime(messageDTO.getDateTime());
        Message message = new Message();
        message.setContent(messageDTOToAdmin.getContent());
        message.setDateTime(LocalDateTime.parse(messageDTO.getDateTime(), DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm:ss")));
        this.messageRepository.save(message);
    }

    private MessageDTO getMessageDTOFromMessage(Message m) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(m.getId());
        messageDTO.setContent(m.getContent());
        messageDTO.setDateTime(m.getDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm:ss")));
        UserMessageDTO senderDTO = new UserMessageDTO();
        if (m.getSender() != null) {
            senderDTO.setUsername(m.getSender().getUsername());
            senderDTO.setFirstName(m.getSender().getFirstName());
            senderDTO.setLastName(m.getSender().getLastName());
        }
        messageDTO.setSender(senderDTO);
        UserMessageDTO receiverDTO = new UserMessageDTO();
        if (m.getReceiver() != null) {
            receiverDTO.setUsername(m.getReceiver().getUsername());
            receiverDTO.setFirstName(m.getReceiver().getFirstName());
            receiverDTO.setLastName(m.getReceiver().getLastName());
        }
        messageDTO.setReceiver(receiverDTO);
        return messageDTO;
    }
}
