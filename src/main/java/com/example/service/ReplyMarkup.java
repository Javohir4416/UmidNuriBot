package com.example.service;
import com.example.entity.User;
import com.example.payload.enums.UserStateNames;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyMarkup {
    public ReplyKeyboardMarkup markup(User user) {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> rowList = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardButton row1Button1 = new KeyboardButton();
        if(user.getUserState().equals(UserStateNames.START.name())){
            KeyboardButton row1Button2 = new KeyboardButton();
            KeyboardButton row1Button3 = new KeyboardButton();
            KeyboardButton row1Button4 = new KeyboardButton();
            row1Button1.setText("Psixologik muammo");
            row1Button2.setText("Moliyaviy muammo");
            row1Button3.setText("Tashkiliy muammo");
            row1Button4.setText("Shaxsiy rivojlanish uchun kurslar");
            row1.add(row1Button1);
            row1.add(row1Button2);
            row1.add(row1Button3);
            row1.add(row1Button4);
            rowList.add(row1);
        }
         if (user.getUserState().equals(UserStateNames.PSYCHOLOGY.name())) {
             KeyboardRow row2 = new KeyboardRow();
             KeyboardRow row3 = new KeyboardRow();
             KeyboardButton row2Button1 = new KeyboardButton();
             KeyboardButton row3Button1 = new KeyboardButton();
             row1Button1.setText("Ruhiy ko’mak");
             row2Button1.setText("Malakaviy psixolog maslahati");
             row3Button1.setText("Bosh Menu");
             row1.add(row1Button1);
             row2.add(row2Button1);
             row3.add(row3Button1);
             rowList.add(row1);
             rowList.add(row2);
             rowList.add(row3);
        }
        if (user.getUserState().equals(UserStateNames.FINANCIAL.name())) {
            KeyboardRow row2 = new KeyboardRow();
            KeyboardRow row3 = new KeyboardRow();
            KeyboardRow row4 = new KeyboardRow();
            KeyboardButton row2Button1 = new KeyboardButton();
            KeyboardButton row3Button1 = new KeyboardButton();
            KeyboardButton row4Button1 = new KeyboardButton();
            row1Button1.setText("Turar joy");
            row2Button1.setText("Kontrakt to’lovi");
            row3Button1.setText("Ish topish");
            row4Button1.setText("Bosh Menu");
            row1.add(row1Button1);
            row2.add(row2Button1);
            row3.add(row3Button1);
            row4.add(row4Button1);
            rowList.add(row1);
            rowList.add(row2);
            rowList.add(row3);
            rowList.add(row4);
        }
        if (user.getUserState().equals(UserStateNames.OTHER.name())) {
            KeyboardRow row2 = new KeyboardRow();
            KeyboardRow row3 = new KeyboardRow();
            KeyboardRow row4 = new KeyboardRow();
            KeyboardButton row2Button1 = new KeyboardButton();
            KeyboardButton row3Button1 = new KeyboardButton();
            KeyboardButton row4Button1 = new KeyboardButton();
            row1Button1.setText("Kafedra/dekanat o’qituvchilari bilan tushinmovchiliklar");
            row2Button1.setText("Huquqlar poymol qilinishi");
            row3Button1.setText("Til bilmaslik");
            row4Button1.setText("Bosh Menu");
            row1.add(row1Button1);
            row2.add(row2Button1);
            row3.add(row3Button1);
            row4.add(row4Button1);
            rowList.add(row1);
            rowList.add(row2);
            rowList.add(row3);
            rowList.add(row4);
        }
        if (user.getUserState().equals(UserStateNames.FIND_JOB.name())) {
            KeyboardRow row2 = new KeyboardRow();
            KeyboardRow row3 = new KeyboardRow();
            KeyboardRow row4 = new KeyboardRow();
            KeyboardRow row5 = new KeyboardRow();
            KeyboardRow row6 = new KeyboardRow();
            KeyboardButton row2Button1 = new KeyboardButton();
            KeyboardButton row3Button1 = new KeyboardButton();
            KeyboardButton row4Button1 = new KeyboardButton();
            KeyboardButton row5Button1 = new KeyboardButton();
            row1Button1.setText("Rus tili");
            row2Button1.setText("Ingliz tili");
            row3Button1.setText("SMM");
            row4Button1.setText("Boshqa sabablar");
            row5Button1.setText("Bosh Menu");
            row1.add(row1Button1);
            row2.add(row2Button1);
            row3.add(row3Button1);
            row4.add(row4Button1);
            row5.add(row5Button1);
            rowList.add(row1);
            rowList.add(row2);
            rowList.add(row3);
            rowList.add(row4);
            rowList.add(row5);
        }
        if (user.getUserState().equals(UserStateNames.PERSONAL_DEVELOPMENT.name())) {
            KeyboardRow row2 = new KeyboardRow();
            KeyboardRow row3 = new KeyboardRow();
            KeyboardRow row4 = new KeyboardRow();
            KeyboardRow row5 = new KeyboardRow();
            KeyboardRow row6 = new KeyboardRow();
            KeyboardButton row2Button1 = new KeyboardButton();
            KeyboardButton row3Button1 = new KeyboardButton();
            KeyboardButton row4Button1 = new KeyboardButton();
            KeyboardButton row5Button1 = new KeyboardButton();
            KeyboardButton row6Button1 = new KeyboardButton();
            row1Button1.setText("Rus tili");
            row2Button1.setText("Ingliz tili");
            row3Button1.setText("SMM");
            row4Button1.setText("Sport to'garaklari");
            row5Button1.setText("Fitness");
            row6Button1.setText("Bosh Menu");
            row1.add(row1Button1);
            row2.add(row2Button1);
            row3.add(row3Button1);
            row4.add(row4Button1);
            row5.add(row5Button1);
            row6.add(row6Button1);
            rowList.add(row1);
            rowList.add(row2);
            rowList.add(row3);
            rowList.add(row4);
            rowList.add(row5);
            rowList.add(row6);
        }
        if (user.getUserState().equals(UserStateNames.ENTER_PASSWORD_FOR_ADMIN.name())) {
            KeyboardRow row2 = new KeyboardRow();
            KeyboardRow row3 = new KeyboardRow();
            KeyboardRow row4 = new KeyboardRow();
            KeyboardRow row5 = new KeyboardRow();
            KeyboardRow row6 = new KeyboardRow();
            KeyboardButton row2Button1 = new KeyboardButton();
            KeyboardButton row3Button1 = new KeyboardButton();
            KeyboardButton row4Button1 = new KeyboardButton();
            KeyboardButton row5Button1 = new KeyboardButton();
            KeyboardButton row6Button1 = new KeyboardButton();
            row1Button1.setText("Psixologik muammolar uchun qabul qilingan so'rovlar");
            row2Button1.setText("Kafedra bilan muammolar");
            row3Button1.setText("Huquqlar poymol bo'lishi");
            row4Button1.setText("Turar joy yoki kontarkt to'lovi");
            row5Button1.setText("Statistika");
            row6Button1.setText("Bosh Menu");
            row1.add(row1Button1);
            row2.add(row2Button1);
            row3.add(row3Button1);

            row4.add(row4Button1);
            row5.add(row5Button1);
            row6.add(row6Button1);
            rowList.add(row1);
            rowList.add(row2);
            rowList.add(row3);
            rowList.add(row4);
            rowList.add(row5);
            rowList.add(row6);
        }
        markup.setResizeKeyboard(true);
        markup.setKeyboard(rowList);
        return markup;
    }
}
