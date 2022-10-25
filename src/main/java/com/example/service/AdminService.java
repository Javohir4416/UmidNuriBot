package com.example.service;
import com.example.constants.RestConstants;
import com.example.entity.User;
import com.example.feign.TelegramFeign;
import com.example.payload.enums.UserStateNames;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserService userService;
    private final TelegramFeign telegramFeign;
    private final ReplyMarkup replyMarkup;

    public void enterPasswordForAdmin(Update update) {
        User user = userService.getUserFromUpdate(update);
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(user.getChatId());
        sendMessage.setText("Parolni kiriting ");
        user.setUserState(UserStateNames.ENTER_PASSWORD_FOR_ADMIN.name());
        telegramFeign.sendMessageToUser(sendMessage);
    }

    public void checkPassword(Update update) {
        String password = update.getMessage().getText();
        User user = userService.getUserFromUpdate(update);
        if(password.equals(RestConstants.PASSWORD)) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(user.getChatId());
            sendMessage.setText("Salom admin ");
            sendMessage.setReplyMarkup(replyMarkup.markup(user));
            telegramFeign.sendMessageToUser(sendMessage);
        }
        else {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(user.getChatId());
            sendMessage.setText("Parol xato . Qaytadan kiriting");
            telegramFeign.sendMessageToUser(sendMessage);
        }
    }
}
