package bg.softuni.pochivka.model.dto;


public class MessageDTO {
    private long id;
    private String content;
    private String dateTime;
    private UserMessageDTO sender;
    private UserMessageDTO receiver;
    private String receiverName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public UserMessageDTO getSender() {
        return sender;
    }

    public void setSender(UserMessageDTO sender) {
        this.sender = sender;
    }

    public UserMessageDTO getReceiver() {
        return receiver;
    }

    public void setReceiver(UserMessageDTO receiver) {
        this.receiver = receiver;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
}
