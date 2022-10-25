package com.example.feign;

import com.example.constants.RestConstants;
import com.example.payload.ResultTelegram;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.File;

@FeignClient(url = RestConstants.TELEGRAM_BASE_URL,name = "TelegramFeign")
public interface TelegramFeign {



    @PostMapping("/bot"+RestConstants.BOT_TOKEN+"/sendMessage")
    ResultTelegram sendMessageToUser(@RequestBody  SendMessage sendMessage);

    @PostMapping("/bot"+RestConstants.BOT_TOKEN+"/sendDocument")
    ResultTelegram sendDocumentToUser(@RequestBody SendDocument sendDocument);
}
