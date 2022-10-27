package com.example.service;
import com.example.constants.RestConstants;
import com.example.entity.User;
import com.example.feign.TelegramFeign;
import com.example.payload.enums.UserStateNames;
import com.example.repo.UserRepo;
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
    private final UserRepo userRepo;

    public void enterPasswordForAdmin(Update update) {
        try {
            User user = userService.getUserFromUpdate(update);
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(user.getId().toString());
            sendMessage.setText("Parolni kiriting ");
            user.setUserState(UserStateNames.ENTER_PASSWORD_FOR_ADMIN.name());
            userRepo.saveAndFlush(user);
            telegramFeign.sendMessageToUser(sendMessage);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void checkPassword(Update update) {
        try {
            String password = update.getMessage().getText();
            User user = userService.getUserFromUpdate(update);
            if (password.equals(RestConstants.PASSWORD)) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(user.getId().toString());
                sendMessage.setText("Salom admin ");
                sendMessage.setReplyMarkup(replyMarkup.markup(user));
                telegramFeign.sendMessageToUser(sendMessage);
            } else {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(user.getId().toString());
                sendMessage.setText("Parol xato . Qaytadan kiriting");
                telegramFeign.sendMessageToUser(sendMessage);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
