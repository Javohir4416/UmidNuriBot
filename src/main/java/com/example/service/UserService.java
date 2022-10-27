package com.example.service;
import com.example.entity.User;
import com.example.feign.TelegramFeign;
import com.example.payload.SendDocumentOwn;
import com.example.payload.SendPhotoOwn;
import com.example.payload.enums.UserStateNames;
import com.example.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private  String text=null;

    private final TelegramFeign telegramFeign;
    private final ReplyMarkup replyMarkup;
    private final UserRepo userRepo;

    public User getUserFromUpdate(Update update){
        if (update.hasMessage()){
            org.telegram.telegrambots.meta.api.objects.User userFromUpdate = update.getMessage().getFrom();
            Long id = userFromUpdate.getId();
            Optional<User> optionalUser = userRepo.findById(id);
            User user;
            user = optionalUser.orElseGet(() -> new User(
                    userFromUpdate.getId().toString(),
                    1,
                    1,
                    1,
                    1,
                    " ",
                    " ",
                    " ",
                    " ",
                    UserStateNames.START.name()));
            user=userRepo.save(user);
            return user;
        }
        else {
            Long id = update.getCallbackQuery().getFrom().getId();
            Optional<User> optionalUser = userRepo.findById(id);
            User user=new User();
            if (optionalUser.isPresent()) {
                user=optionalUser.get();
            }
            return user;
        }
    }

    public void showMenu(Update update) {
        try {
            User user = getUserFromUpdate(update);
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(user.getChatId());
            sendMessage.setText("Qanday muammoyingiz bor ? ");
            user.setUserState(UserStateNames.START.name());
            user=userRepo.save(user);
            sendMessage.setReplyMarkup(replyMarkup.markup(user));
            telegramFeign.sendMessageToUser(sendMessage);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void stats(Update update){
        try {
            User user = getUserFromUpdate(update);
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(user.getChatId());
            sendMessage.setText("Bot foydalanuvchilari soni : " + userRepo.findAll().size());
            telegramFeign.sendMessageToUser(sendMessage);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void psychologyProblem(Update update) {
        try {
            User user = getUserFromUpdate(update);
            user.setUserState(UserStateNames.PSYCHOLOGY.name());
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(user.getChatId());
            sendMessage.setText("Qay biriga muhtojsiz ? ");
            user=userRepo.save(user);
            sendMessage.setReplyMarkup(replyMarkup.markup(user));
            telegramFeign.sendMessageToUser(sendMessage);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void questions(Update update) {
        try {
            User user = getUserFromUpdate(update);
            user.setUserState(UserStateNames.QUESTION.name());
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(user.getChatId());
            sendMessage.setText("Savolingizni yozib qoldiring");
            user.setUserState(UserStateNames.QUESTION.name());
            userRepo.save(user);
            telegramFeign.sendMessageToUser(sendMessage);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void questionsForPsychology(Update update) {
        try {
            String question = update.getMessage().getText();
            User user = getUserFromUpdate(update);
            user.setUserState(UserStateNames.START.name());
            user.setQuestionForPsychology(question);
            userRepo.save(user);
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(user.getChatId());
            sendMessage.setText("Savolga javobini 24 soat ichida https://t.me/leadergirls_umidnuri dan topasiz.\n" +
                    "Bizga ishonch bildirganingiz uchun rahmat.Qo’shimcha savollar bo’lsa murojaat uchun @NuriG2\n");
            telegramFeign.sendMessageToUser(sendMessage);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void error(Update update) {
        try {
            User user = getUserFromUpdate(update);
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(user.getChatId());
            sendMessage.setText("Tushunarsiz buyruq");
            telegramFeign.sendMessageToUser(sendMessage);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void DBForQuestions(Update update) {
        try {
            String questions = "";
            User user = getUserFromUpdate(update);
            user.setUserState(UserStateNames.START.name());
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(user.getChatId());
            int i=0;
            if (!userRepo.findAll().isEmpty()) {
                for (User user1 : userRepo.findAll()) {
                    if (!user1.getQuestionForPsychology().equals(" ")) {
                        questions = ((++i) + "." + user1.getQuestionForPsychology() + "\n");
                        sendMessage.setText(questions);
                        user1.setQuestionForPsychology(" ");
                        userRepo.save(user1);
                        telegramFeign.sendMessageToUser(sendMessage);
                    }
                }
                if(i==0) {
                    sendMessage.setText("Qabul qilingan savollar yo'q");
                    telegramFeign.sendMessageToUser(sendMessage);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void financialProblems(Update update) {
        try {
            User user = getUserFromUpdate(update);
            user.setUserState(UserStateNames.FINANCIAL.name());
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(user.getChatId());
            sendMessage.setText("Quyidagi muammolardan qaysi biri sizning vaziyatingizga mos tushadi ?");
            sendMessage.setReplyMarkup(replyMarkup.markup(user));
            telegramFeign.sendMessageToUser(sendMessage);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void generalPerson(Update update) {
        try {
            User user = getUserFromUpdate(update);
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(user.getChatId());
            sendMessage.setText("Murojaat uchun\n" +
                    "@NuriG2\n");
            telegramFeign.sendMessageToUser(sendMessage);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void otherProblems(Update update) {
            try {
                User user = getUserFromUpdate(update);
                user.setUserState(UserStateNames.OTHER.name());
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(user.getChatId());
                sendMessage.setText("Quyidagi muammolardan qaysi biri sizning vaziyatingizga mos tushadi ?");
                sendMessage.setReplyMarkup(replyMarkup.markup(user));
                telegramFeign.sendMessageToUser(sendMessage);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
    }

    public void personalDevelopment(Update update) {
        try {
            User user = getUserFromUpdate(update);
            user.setUserState(UserStateNames.PERSONAL_DEVELOPMENT.name());
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(user.getChatId());
            sendMessage.setText("Kategoriyalardan birini tanlang ");
            sendMessage.setReplyMarkup(replyMarkup.markup(user));
            telegramFeign.sendMessageToUser(sendMessage);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void findJob(Update update) {
        try {
            User user = getUserFromUpdate(update);
            user.setUserState(UserStateNames.FIND_JOB.name());
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(user.getChatId());
            sendMessage.setText("Ish topishga nima to’sqinlik qilyapti ");
            sendMessage.setReplyMarkup(replyMarkup.markup(user));
            telegramFeign.sendMessageToUser(sendMessage);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void problemWithGovernment(Update update) {
        try {
            User user = getUserFromUpdate(update);
            user.setUserState(UserStateNames.PROBLEM_WITH_GOVERNMENT.name());
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(user.getChatId());
            sendMessage.setText("O'qituvchi ism, familiyasi va kafedrasini va tushunmovchilik sababini ko'rsating");
            telegramFeign.sendMessageToUser(sendMessage);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void questionsForGovernment(Update update) {
        try {
            String question = update.getMessage().getText();
            User user = getUserFromUpdate(update);
            user.setQuestionForGovernment(question);
            user.setSTATS_FOR_GOVERNMENT(user.getSTATS_FOR_GOVERNMENT()+1);
            user.setUserState(UserStateNames.START.name());
            userRepo.save(user);
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(user.getChatId());
            sendMessage.setText("So'rov qabul qilindi . Qo’shimcha savollar bo’lsa murojaat uchun @NuriG2");
            telegramFeign.sendMessageToUser(sendMessage);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void problemWithRights(Update update) {
        try {
            User user = getUserFromUpdate(update);
            user.setUserState(UserStateNames.PROBLEM_WITH_RIGHTS.name());
            userRepo.save(user);
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(user.getChatId());
            sendMessage.setText("Kim tomonidan qanday huquqbuzarlik sodir etildi?");
            telegramFeign.sendMessageToUser(sendMessage);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void questionsForRights(Update update) {
        try {
            String question = update.getMessage().getText();
            User user = getUserFromUpdate(update);
            user.setQuestionForRight(question);
            user.setUserState(UserStateNames.START.name());
            user.setSTATS_FOR_RIGHTS(user.getSTATS_FOR_RIGHTS()+1);
            userRepo.save(user);
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(user.getChatId());
            sendMessage.setText("So'rov qabul qilindi . Qo’shimcha savollar bo’lsa murojaat uchun @NuriG2");
            telegramFeign.sendMessageToUser(sendMessage);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void DBForGovernment(Update update) {
        try {
            String questions = "";
            User user = getUserFromUpdate(update);
            user.setUserState(UserStateNames.START.name());
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(user.getChatId());
            int i=0;
            if (!userRepo.findAll().isEmpty()) {
                for (User user1 : userRepo.findAll()) {
                    if (!user1.getQuestionForGovernment().equals(" ")) {
                        questions = ((++i) + "." + user1.getQuestionForGovernment() + "\n");
                        sendMessage.setText(questions);
                        user1.setQuestionForGovernment(" ");
                        userRepo.save(user1);
                        telegramFeign.sendMessageToUser(sendMessage);
                    }
                }
                if(i==0) {
                    sendMessage.setText("Qabul qilingan savollar yo'q");
                    telegramFeign.sendMessageToUser(sendMessage);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void DBForRights(Update update) {
        try {
            String questions = "";
            User user = getUserFromUpdate(update);
            user.setUserState(UserStateNames.START.name());
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(user.getChatId());
            int i=0;
            if (!userRepo.findAll().isEmpty()) {
                for (User user1 : userRepo.findAll()) {
                    if (!user1.getQuestionForRight().equals(" ")) {
                        questions = ((++i) + "." + user1.getQuestionForRight() + "\n");
                        sendMessage.setText(questions);
                        user1.setQuestionForRight(" ");
                        userRepo.save(user1);
                        telegramFeign.sendMessageToUser(sendMessage);
                    }
                }
                if(i==0) {
                    sendMessage.setText("Qabul qilingan savollar yo'q");
                    telegramFeign.sendMessageToUser(sendMessage);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void statics(Update update) {
        try {
            User user = getUserFromUpdate(update);
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(user.getChatId());
            List<User> allUsers = userRepo.findAll();
            int psy=0;
            int gov=0;
            int right=0;
            int workHome=0;
            if (!allUsers.isEmpty()) {
                for (User allUser : allUsers) {
                    psy=psy+allUser.getSTATS_FOR_PSYCHOLOGY();
                    gov=gov+allUser.getSTATS_FOR_GOVERNMENT();
                    right=right+allUser.getSTATS_FOR_RIGHTS();
                    workHome=workHome+allUser.getSTATS_FOR_WORK_OR_HOME();
                }
                sendMessage.setText("1.Psixologik muammolar soni : " + psy + "\n" +
                        "2.Kafedra bilan muammolar soni : " + gov + "\n" +
                        "3.Huquqlar poymol bo'lishi soni : " + right + "\n" +
                        "4.Kontrakt to'lovi yoki turar joy muammosi : " + workHome);
            }
            telegramFeign.sendMessageToUser(sendMessage);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void showReason(Update update) {
        try {
            text = update.getMessage().getText();
            User user = getUserFromUpdate(update);
            user.setUserState(UserStateNames.SHOW_REASON.name());
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(user.getChatId());
            sendMessage.setText("Asosni ko'rsating (Pdf yoki rasm yuklang)");
            telegramFeign.sendMessageToUser(sendMessage);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void getFile(Update update) {

        try {
            User user = getUserFromUpdate(update);
            user.setSTATS_FOR_WORK_OR_HOME(user.getSTATS_FOR_WORK_OR_HOME()+1);
            if (update.getMessage().hasDocument()) {
                String fileId = update.getMessage().getDocument().getFileId();
                user.setFileId(fileId);
                user.setCaption(text);
                user.setPhoto(false);
            } else if (update.getMessage().hasPhoto()) {
                List<PhotoSize> photo = update.getMessage().getPhoto();
                String fileId = photo.get(0).getFileId();
                user.setFileId(fileId);
                user.setCaption(text);
                user.setPhoto(true);
            }
            userRepo.save(user);
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(user.getChatId());
            sendMessage.setText("72 soat ichida holatingizni o’rganib chiqqan holda sizga bog’lanamiz ");
            telegramFeign.sendMessageToUser(sendMessage);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void groupAndContact(Update update) {
        try {
            User user = getUserFromUpdate(update);
            user.setUserState(UserStateNames.GROUP_CONTACT.name());
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(user.getChatId());
            sendMessage.setText("Ism familiya , guruh va bog’lanish uchun nomer qoldiring");
            telegramFeign.sendMessageToUser(sendMessage);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void contractOrHome(Update update) {
        try {
            User user = getUserFromUpdate(update);
            List<User> allUser = userRepo.findAll();
            int i=0;
            if (!allUser.isEmpty()) {
                for (User user1 : allUser) {
                if (user1.isPhoto()) {
                    SendPhotoOwn sendPhotoOwn = new SendPhotoOwn();
                    sendPhotoOwn.setChatId(user.getChatId());
                    sendPhotoOwn.setPhoto(user1.getFileId());
                    sendPhotoOwn.setCaption(user1.getCaption());
                    if(!user1.getFileId().equals(" ")) {
                        telegramFeign.sendPhotoToUser(sendPhotoOwn);
                        i++;
                        user.setFileId(" ");
                        userRepo.save(user);
                    }
                } else {
                    SendDocumentOwn sendDocumentOwn = new SendDocumentOwn();
                    sendDocumentOwn.setChatId(user.getChatId());
                    sendDocumentOwn.setDocument(user1.getFileId());
                    sendDocumentOwn.setCaption(user1.getCaption());
                    if(!user1.getFileId().equals(" ")) {
                        telegramFeign.sendDocument(sendDocumentOwn);
                        i++;
                        user.setFileId(" ");
                        userRepo.save(user);
                    }
                }
            }
                if(i==0){
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(user.getChatId());
                    sendMessage.setText("Hozircha qabul qilingan murojaatlar yo'q");
                    telegramFeign.sendMessageToUser(sendMessage);
                }

        }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
