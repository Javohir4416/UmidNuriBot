package com.example.controller;
import com.example.service.TelegramService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/telegram")
public class WebHookController {

    private final TelegramService telegramService;


    @PostMapping
    public void getUpdates(@RequestBody Update update) {
        telegramService.getUpdates(update);
    }
}
