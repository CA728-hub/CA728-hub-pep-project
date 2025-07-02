package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {

    MessageDAO messageDAO = new MessageDAO();

    public MessageService() {}

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message createMessage(Message message) {
        if (message.getMessage_text().length() > 0 && message.getMessage_text().length() < 255) {
            return messageDAO.insertMessage(message);
        }
        return null;
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }


    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
}



    public Message deleteMessageById(int messageId) {
        Message message = messageDAO.getMessageById(messageId);
        if (message != null) {
            messageDAO.deleteMessageById(messageId);
        }
        return message;
    }

    public Message updateMessageById(int messageId, String newText) {
        if (newText != null && newText.length() > 0 && newText.length() < 255) {
            Message existing = messageDAO.getMessageById(messageId);
            if (existing != null) {
                existing.setMessage_text(newText);
                messageDAO.updateMessageById(messageId, newText);
                return existing;
            }
        }
        return null;
    }

    public List<Message> getMessagesByUserId(int accountId) {
        return messageDAO.getMessagesByUserId(accountId);
    }
}
