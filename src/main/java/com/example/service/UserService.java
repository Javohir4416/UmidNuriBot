package com.example.service;

import com.example.database.Database;
import com.example.entity.User;
import com.example.feign.TelegramFeign;
import com.example.payload.enums.UserStateNames;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.LinkedList;
import java.util.Queue;

@Service
@RequiredArgsConstructor
public class UserService {

    private final TelegramFeign telegramFeign;
    private final Queue<String> queue=new LinkedList<>();
    private final ReplyMarkup replyMarkup;
    public  User getOrCreateUser(String chatId) {
        for (User user : Database.users) {
            if (user.getChatId().equals(chatId)) {
                return user;
            }
        }
        User user = new User(chatId, UserStateNames.START.name());
        Database.users.add(user);
        return user;
    }
    public  User getUserFromUpdate(Update update) {
        if (update.hasMessage()) {
            return getOrCreateUser(update.getMessage().getChatId().toString());
        } else if (update.hasCallbackQuery()) {
            return getOrCreateUser(update.getCallbackQuery().getMessage().getChatId().toString());
        }
        return new User();
    }

    public void showMenu(Update update) {
        User user = getUserFromUpdate(update);
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(user.getChatId());
        sendMessage.setText("Qanday muammoyingiz bor ? ");
        user.setUserState(UserStateNames.START.name());
        sendMessage.setReplyMarkup(replyMarkup.markup(user));
        telegramFeign.sendMessageToUser(sendMessage);
    }

    public void stats(Update update) {
        User user = getUserFromUpdate(update);
        user.setUserState(UserStateNames.START.name());
        SendMessage sendMessage =new SendMessage();
        sendMessage.setChatId(user.getChatId());
        sendMessage.setText("Bot foydalanuvchilari soni : "+ Database.users.size());
        telegramFeign.sendMessageToUser(sendMessage);
    }

    public void psychologyProblem(Update update) {
        User user = getUserFromUpdate(update);
        user.setUserState(UserStateNames.PSYCHOLOGY.name());
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(user.getChatId());
        sendMessage.setText("Qay biriga muhtojsiz ? ");
        sendMessage.setReplyMarkup(replyMarkup.markup(user));
        telegramFeign.sendMessageToUser(sendMessage);
    }

    public void questions(Update update) {
        User user = getUserFromUpdate(update);
        user.setUserState(UserStateNames.QUESTION.name());
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(user.getChatId());
        sendMessage.setText("Savolingizni yozib qoldiring");
        user.setUserState(UserStateNames.QUESTION.name());
        telegramFeign.sendMessageToUser(sendMessage);
    }

    public void forQuestions(Update update) {
        String question = update.getMessage().getText();
        queue.add(question);
        User user = getUserFromUpdate(update);
        user.setUserState(UserStateNames.START.name());
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(user.getChatId());
        sendMessage.setText("Savolga javobini 24 soat ichida https://t.me/leadergirls_umidnuri dan topasiz.\n" +
                "Bizga ishonch bildirganingiz uchun rahmat.\n");
        telegramFeign.sendMessageToUser(sendMessage);
    }

    public void error(Update update) {
        User user = getUserFromUpdate(update);
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(user.getChatId());
        sendMessage.setText("Tushunarsiz buyruq");
        telegramFeign.sendMessageToUser(sendMessage);
    }

    public void DBforQuestions(Update update) {
        String questions="";
        User user = getUserFromUpdate(update);
        user.setUserState(UserStateNames.START.name());
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(user.getChatId());
        int size = queue.size();
        if(!queue.isEmpty()){
            for (int i = 0; i < size; i++) {
                questions+=((i+1)+"."+queue.poll()+"\n");
            }
            sendMessage.setText(questions);
        }
        else sendMessage.setText("Qabul qilingan savollar yo'q");
        telegramFeign.sendMessageToUser(sendMessage);
    }

    public void financialProblems(Update update) {
        User user = getUserFromUpdate(update);
        user.setUserState(UserStateNames.FINANCIAL.name());
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(user.getChatId());
        sendMessage.setText("Quyidagi muammolardan qaysi biri sizning vaziyatingizga mos tushadi ?");
        sendMessage.setReplyMarkup(replyMarkup.markup(user));
        telegramFeign.sendMessageToUser(sendMessage);
    }

    public void generalPerson(Update update) {
        User user = getUserFromUpdate(update);
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(user.getChatId());
        sendMessage.setText("Murojaat uchun\n" +
                "@NuriG2\n");
        telegramFeign.sendMessageToUser(sendMessage);
    }

    public void otherProblems(Update update) {
            User user = getUserFromUpdate(update);
            user.setUserState(UserStateNames.OTHER.name());
            SendMessage sendMessage=new SendMessage();
            sendMessage.setChatId(user.getChatId());
            sendMessage.setText("Quyidagi muammolardan qaysi biri sizning vaziyatingizga mos tushadi ?");
            sendMessage.setReplyMarkup(replyMarkup.markup(user));
            telegramFeign.sendMessageToUser(sendMessage);
    }
}
