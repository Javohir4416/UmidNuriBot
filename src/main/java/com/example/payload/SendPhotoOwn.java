package com.example.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Data
@NoArgsConstructor
public class SendPhotoOwn {
    @JsonProperty(value = "chat_id")
    private String chatId;
    private String caption;
    private String photo;


    public SendPhotoOwn(String chatId, String  photo) {
        this.chatId = chatId;
        this.photo = photo;
    }

    public SendPhotoOwn(String chatId, String caption, String photo) {
        this.chatId = chatId;
        this.caption = caption;
        this.photo = photo;
    }
}
