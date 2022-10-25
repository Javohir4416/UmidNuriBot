package com.example.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendDocumentOwn {
    @JsonProperty(value = "chat_id")
    private String chatId;
    private String caption;
    private String document;

    @JsonProperty("reply_markup")
    private ReplyKeyboard replyKeyboard;

    public SendDocumentOwn(String chatId, String  document) {
        this.chatId = chatId;
        this.document = document;
    }

    public SendDocumentOwn(String chatId, String caption, String document) {
        this.chatId = chatId;
        this.caption = caption;
        this.document = document;
    }
}
