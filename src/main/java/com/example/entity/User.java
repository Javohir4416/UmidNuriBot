package com.example.entity;

import com.example.payload.enums.UserStateNames;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private UUID id=UUID.randomUUID();

    private String chatId;
    private String userState;
    private String firstName;
    private String lastName;
    private String fileId;

    public User(String id) {
        this.chatId = id;
    }

    public User(String id, String userState) {
        this.chatId = id;
        this.userState = userState;
    }
}
