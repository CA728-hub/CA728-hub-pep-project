package Controller;

import DAO.AccountDAO;
import Model.Account;
import Model.Message;
import DAO.MessageDAO;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;


import java.util.List; 

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    private final AccountService accountService = new AccountService();
    private final MessageService messageService = new MessageService();

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
    
        app.post("/register", this::handleRegisterUser);
        app.post("/login", this::handleLoginUser);
        app.post("/messages", this::handleCreateMessage);
        app.get("/messages", this::handleGetAllMessages);
        app.get("/messages/{message_id}", this::handleGetMessageById);
        app.delete("/messages/{message_id}", this::handleDeleteMessageById);
        app.patch("/messages/{message_id}", this::handleUpdateMessageById);
        app.get("/accounts/{account_id}/messages", this::handleGetMessagesByUserId);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }



    private void handleRegisterUser(Context ctx) {
        Account account = ctx.bodyAsClass(Account.class);
        Account createdAccount = accountService.registerUser(account);
        if (createdAccount != null) {
            ctx.status(200).json(createdAccount);
        } else {
            ctx.status(400);
        }
    }


    private void handleLoginUser(Context ctx) {
        Account account = ctx.bodyAsClass(Account.class);
        Account loggedIn = accountService.loginUser(account.getUsername(), account.getPassword());
        if (loggedIn != null) {
            ctx.status(200).json(loggedIn);
        } else {
            ctx.status(401);
        }
    }

    private void handleCreateMessage(Context ctx) {
        Message message = ctx.bodyAsClass(Message.class);
        Message created = messageService.createMessage(message);
        if (created != null) {
            ctx.status(200).json(created);
        } else {
            ctx.status(400);
        }
    }


    private void handleGetAllMessages(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.status(200).json(messages);
    }




    private void handleGetMessageById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(id);

        if (message != null) {
            ctx.status(200).json(message); 
        } else {
            ctx.status(200).result("");    
        }
    }




    private void handleDeleteMessageById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message deleted = messageService.deleteMessageById(id);
        if (deleted != null) {
            ctx.status(200).json(deleted);
        } else {
            ctx.status(200);  
        }
    }

    private void handleUpdateMessageById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = ctx.bodyAsClass(Message.class);
        Message updated = messageService.updateMessageById(id, message.getMessage_text());
        if (updated != null) {
            ctx.status(200).json(updated);
        } else {
            ctx.status(400);
        }
    }

    private void handleGetMessagesByUserId(Context ctx) {
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByUserId(accountId);
        ctx.status(200).json(messages);
    }
}