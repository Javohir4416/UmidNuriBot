package com.example.service;

import com.example.entity.User;
import com.example.feign.TelegramFeign;
import com.example.payload.enums.UserStateNames;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class TelegramService {
    private final UserService userService;
    private final AdminService adminService;


    private final TelegramFeign telegramFeign;



    public void getUpdates(Update update)  {
        if (update.hasMessage()) {
            if (update.getMessage().hasContact()) {
                User user = userService.getUserFromUpdate(update);
                SendMessage sendMessage=new SendMessage(user.getId().toString(),"Mavjud bo'lmagan buyruq");
               telegramFeign.sendMessageToUser(sendMessage);
            }
            else if (update.getMessage().hasLocation()) {
                User user = userService.getUserFromUpdate(update);
                SendMessage sendMessage=new SendMessage(user.getId().toString(),"Mavjud bo'lmagan buyruq");
                telegramFeign.sendMessageToUser(sendMessage);
        }
            else {
                String text = update.getMessage().getText();
                if(text!=null) {
                    switch (text) {
                        case "/start":
                        case "Bosh Menu":
                            userService.showMenu(update);
                            break;

                        case "/admin":
                            adminService.enterPasswordForAdmin(update);
                            break;

                        case "/stats":
                            userService.stats(update);
                            break;

                        case "Psixologik muammo":
                            userService.psychologyProblem(update);
                            break;

                        case "Ruhiy ko’mak":
                        case "Malakaviy psixolog maslahati":
                            userService.questions(update);
                            break;

                        case "Psixologik muammolar uchun qabul qilingan so'rovlar":
                            userService.DBForQuestions(update);
                            break;

                        case "Kafedra bilan muammolar":
                            userService.DBForGovernment(update);
                            break;


                        case "Huquqlar poymol bo'lishi":
                            userService.DBForRights(update);
                            break;


                        case "Moliyaviy muammo":
                            userService.financialProblems(update);
                            break;

                        case "Shaxsiy rivojlanish uchun kurslar":
                            userService.personalDevelopment(update);
                            break;

                        case "Tashkiliy muammo":
                            userService.otherProblems(update);
                            break;


                        case "Ish topish":
                            userService.findJob(update);
                            break;

                        case "Kafedra/dekanat o’qituvchilari bilan tushinmovchiliklar":
                            userService.problemWithGovernment(update);
                            break;

                        case "Huquqlar poymol qilinishi":
                            userService.problemWithRights(update);
                            break;

                        case "Turar joy":
                        case "Kontrakt to’lovi":
                        case "Rus tili":
                        case "Ingliz tili":
                        case "SMM":
                        case "Sport to'garaklari":
                        case "Fitness":
                        case "Til bilmaslik":
                        case "Boshqa sabablar":
                            userService.generalPerson(update);
                            break;
                        default:
                            User user = userService.getUserFromUpdate(update);
                            if(user.getUserState().equals(UserStateNames.QUESTION.name())) {
                                userService.questionsForPsychology(update);
                            }
                            else if(user.getUserState().equals(UserStateNames.PROBLEM_WITH_GOVERNMENT.name())){
                                userService.questionsForGovernment(update);
                            }
                            else if(user.getUserState().equals(UserStateNames.PROBLEM_WITH_RIGHTS.name())){
                                userService.questionsForRights(update);
                            }
                            else if(user.getUserState().equals(UserStateNames.ENTER_PASSWORD_FOR_ADMIN.name())){
                                adminService.checkPassword(update);
                            }
                            else userService.error(update);
                            break;
                    }
                }
            }




        }


        else if (update.hasCallbackQuery()) {

        }
    }
}
