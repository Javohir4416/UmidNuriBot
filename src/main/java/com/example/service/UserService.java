package com.example.service;
import com.example.constants.RestConstants;
import com.example.database.Database;
import com.example.entity.User;
import com.example.feign.TelegramFeign;
import com.example.payload.SendDocumentOwn;
import com.example.payload.enums.UserStateNames;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
@RequiredArgsConstructor
public class UserService {

    Integer STATS_FOR_PSYCHOLOGY=0;
    Integer STATS_FOR_GOVERNMENT=0;
    Integer STATS_FOR_RIGHTS=0;

    private final TelegramFeign telegramFeign;
    private final Queue<String> queueForPsychology=new LinkedList<>();
    private final Queue<String> queueForGovernment=new LinkedList<>();

    private final Queue<String> queueForRights=new LinkedList<>();
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

    public void stats(Update update){
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

    public void questionsForPsychology(Update update) {
        STATS_FOR_PSYCHOLOGY++;
        String question = update.getMessage().getText();
        queueForPsychology.add(question);
        User user = getUserFromUpdate(update);
        user.setUserState(UserStateNames.START.name());
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(user.getChatId());
        sendMessage.setText("Savolga javobini 24 soat ichida https://t.me/leadergirls_umidnuri dan topasiz.\n" +
                "Bizga ishonch bildirganingiz uchun rahmat.Qo’shimcha savollar bo’lsa murojaat uchun @NuriG2\n");
        telegramFeign.sendMessageToUser(sendMessage);
    }

    public void error(Update update) {
        User user = getUserFromUpdate(update);
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(user.getChatId());
        sendMessage.setText("Tushunarsiz buyruq");
        telegramFeign.sendMessageToUser(sendMessage);
    }

    public void DBForQuestions(Update update) {
        String questions="";
        User user = getUserFromUpdate(update);
        user.setUserState(UserStateNames.START.name());
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(user.getChatId());
        int size = queueForPsychology.size();
        if(!queueForPsychology.isEmpty()){
            for (int i = 0; i < size; i++) {
                questions=((i+1)+"."+queueForPsychology.poll()+"\n");
                sendMessage.setText(questions);
                telegramFeign.sendMessageToUser(sendMessage);
            }
        }
        else {
            sendMessage.setText("Qabul qilingan savollar yo'q");
            telegramFeign.sendMessageToUser(sendMessage);
        }
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

    public void personalDevelopment(Update update) {
        User user = getUserFromUpdate(update);
        user.setUserState(UserStateNames.PERSONAL_DEVELOPMENT.name());
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(user.getChatId());
        sendMessage.setText("Kategoriyalardan birini tanlang ");
        sendMessage.setReplyMarkup(replyMarkup.markup(user));
        telegramFeign.sendMessageToUser(sendMessage);
    }

    public void findJob(Update update) {
        User user = getUserFromUpdate(update);
        user.setUserState(UserStateNames.FIND_JOB.name());
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(user.getChatId());
        sendMessage.setText("Ish topishga nima to’sqinlik qilyapti ");
        sendMessage.setReplyMarkup(replyMarkup.markup(user));
        telegramFeign.sendMessageToUser(sendMessage);
    }

    public void problemWithGovernment(Update update) {
        User user = getUserFromUpdate(update);
        user.setUserState(UserStateNames.PROBLEM_WITH_GOVERNMENT.name());
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(user.getChatId());
        sendMessage.setText("O'qituvchi ism, familiyasi va kafedrasini va tushunmovchilik sababini ko'rsating");
        telegramFeign.sendMessageToUser(sendMessage);
    }

    public void questionsForGovernment(Update update) {
        STATS_FOR_GOVERNMENT++;
        String question = update.getMessage().getText();
        queueForGovernment.add(question);
        User user = getUserFromUpdate(update);
        user.setUserState(UserStateNames.START.name());
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(user.getChatId());
        sendMessage.setText("So'rov qabul qilindi . Qo’shimcha savollar bo’lsa murojaat uchun @NuriG2");
        telegramFeign.sendMessageToUser(sendMessage);
    }

    public void problemWithRights(Update update) {
        User user = getUserFromUpdate(update);
        user.setUserState(UserStateNames.PROBLEM_WITH_RIGHTS.name());
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(user.getChatId());
        sendMessage.setText("Kim tomonidan qanday huquqbuzarlik sodir etildi?");
        telegramFeign.sendMessageToUser(sendMessage);
    }

    public void questionsForRights(Update update) {
        STATS_FOR_RIGHTS++;
        String question = update.getMessage().getText();
        queueForRights.add(question);
        User user = getUserFromUpdate(update);
        user.setUserState(UserStateNames.START.name());
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(user.getChatId());
        sendMessage.setText("So'rov qabul qilindi . Qo’shimcha savollar bo’lsa murojaat uchun @NuriG2");
        telegramFeign.sendMessageToUser(sendMessage);
    }

    public void DBForGovernment(Update update) {
        String questions="";
        User user = getUserFromUpdate(update);
        user.setUserState(UserStateNames.START.name());
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(user.getChatId());
        int size = queueForGovernment.size();
        if(!queueForGovernment.isEmpty()){
            for (int i = 0; i < size; i++) {
                questions=((i+1)+"."+queueForGovernment.poll()+"\n");
                sendMessage.setText(questions);
                telegramFeign.sendMessageToUser(sendMessage);
            }
        }
        else {
            sendMessage.setText("Qabul qilingan savollar yo'q");
            telegramFeign.sendMessageToUser(sendMessage);
        }
    }

    public void DBForRights(Update update) {
        String questions="";
        User user = getUserFromUpdate(update);
        user.setUserState(UserStateNames.START.name());
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(user.getChatId());
        int size = queueForRights.size();
        if(!queueForRights.isEmpty()){
            for (int i = 0; i < size; i++) {
                questions=((i+1)+"."+queueForRights.poll()+"\n");
                sendMessage.setText(questions);
                telegramFeign.sendMessageToUser(sendMessage);
            }
        }
        else {
            sendMessage.setText("Qabul qilingan savollar yo'q");
            telegramFeign.sendMessageToUser(sendMessage);
        }
    }

    public void statics(Update update) {
        User user = getUserFromUpdate(update);
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(user.getChatId());
        sendMessage.setText("1.Psixologik muammolar soni : "+STATS_FOR_PSYCHOLOGY+"\n" +
                            "2.Kafedra bilan muammolar soni : "+STATS_FOR_GOVERNMENT+"\n"+
                            "3.Huquuqlar poymol bo'lishi soni : "+STATS_FOR_RIGHTS);
        telegramFeign.sendMessageToUser(sendMessage);
    }

    public void showReason(Update update) {
        User user = getUserFromUpdate(update);
        user.setUserState(UserStateNames.SHOW_REASON.name());
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(user.getChatId());
        sendMessage.setText("Asosni ko'rsating (.pdf yoki .png fromatda) ");
        telegramFeign.sendMessageToUser(sendMessage);
    }

    public void getFile(Update update) {
        if(update.getMessage().hasDocument()){
            User user = getUserFromUpdate(update);
            String fileId = update.getMessage().getDocument().getFileId();
            user.setFileId(fileId);
            SendDocumentOwn sendDocumentOwn=new SendDocumentOwn();
            sendDocumentOwn.setChatId(user.getChatId());
            sendDocumentOwn.setDocument(fileId);
            telegramFeign.sendDocument(sendDocumentOwn);
        }
        else if (update.getMessage().hasPhoto()){

        }
    }
}
