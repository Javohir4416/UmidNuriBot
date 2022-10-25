package com.example.service;
import com.example.database.Database;
import com.example.entity.User;
import com.example.feign.TelegramFeign;
import com.example.payload.SendDocumentOwn;
import com.example.payload.SendPhotoOwn;
import com.example.payload.enums.UserStateNames;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
@RequiredArgsConstructor
public class UserService {

    Integer STATS_FOR_PSYCHOLOGY=0;
    Integer STATS_FOR_GOVERNMENT=0;
    Integer STATS_FOR_RIGHTS=0;

    private  String text=null;

    private Queue<User> userForContractOrHome=new LinkedList<>();

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
        text = update.getMessage().getText();
        User user = getUserFromUpdate(update);
        user.setUserState(UserStateNames.SHOW_REASON.name());
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(user.getChatId());
        sendMessage.setText("Asosni ko'rsating (Pdf yoki rasm yuklang)");
        telegramFeign.sendMessageToUser(sendMessage);
    }

    public void getFile(Update update) {
        User user = getUserFromUpdate(update);
        userForContractOrHome.add(user);
        if(update.getMessage().hasDocument()){
            String fileId = update.getMessage().getDocument().getFileId();
            user.setFileId(fileId);
            user.setCaption(text);
            user.setPhoto(false);
        }
        else if (update.getMessage().hasPhoto()){
            List<PhotoSize> photo = update.getMessage().getPhoto();
            String fileId = photo.get(0).getFileId();
            user.setFileId(fileId);
            user.setCaption(text);
            user.setPhoto(true);
        }
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(user.getChatId());
        sendMessage.setText("72 soat ichida holatingizni o’rganib chiqqan holda sizga bog’lanamiz ");
        telegramFeign.sendMessageToUser(sendMessage);
    }

    public void groupAndContact(Update update) {
        User user = getUserFromUpdate(update);
        user.setUserState(UserStateNames.GROUP_CONTACT.name());
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(user.getChatId());
        sendMessage.setText("Ism familiya , guruh va bog’lanish uchun nomer qoldiring");
        telegramFeign.sendMessageToUser(sendMessage);
    }

    public void contractOrHome(Update update) {
        User user = getUserFromUpdate(update);
        if(userForContractOrHome.isEmpty()){
            SendMessage sendMessage=new SendMessage();
            sendMessage.setChatId(user.getChatId());
            sendMessage.setText("Hozircha murojaatlar yo'q");
            telegramFeign.sendMessageToUser(sendMessage);
        }
        for (int i = 0; i < userForContractOrHome.size(); i++) {
            User userPhoto = userForContractOrHome.poll();
            if (userPhoto.isPhoto()) {
                SendPhotoOwn sendPhotoOwn=new SendPhotoOwn();
                sendPhotoOwn.setChatId(user.getChatId());
                sendPhotoOwn.setPhoto(userPhoto.getFileId());
                sendPhotoOwn.setCaption(userPhoto.getCaption());
                telegramFeign.sendPhotoToUser(sendPhotoOwn);
            }
            else {
                SendDocumentOwn sendDocumentOwn=new SendDocumentOwn();
                sendDocumentOwn.setChatId(user.getChatId());
                sendDocumentOwn.setDocument(userPhoto.getFileId());
                sendDocumentOwn.setCaption(userPhoto.getCaption());
                telegramFeign.sendDocument(sendDocumentOwn);
            }
        }
    }
}
