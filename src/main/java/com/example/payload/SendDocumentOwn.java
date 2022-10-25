package com.example.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class SendDocumentOwn {
    @JsonProperty(value = "chat_id")
    private String chatId;
    private String caption;
    private String document;


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
