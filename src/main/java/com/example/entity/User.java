package com.example.entity;

import com.example.payload.enums.UserStateNames;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public String chatId;
    public int  STATS_FOR_PSYCHOLOGY;
    public int  STATS_FOR_WORK_OR_HOME;
    public int  STATS_FOR_GOVERNMENT;
    public int  STATS_FOR_RIGHTS;
    public String questionForPsychology;
    public String questionForGovernment;
    public String questionForRight;
    public String userState;
    public String firstName;
    public String lastName;
    public String fileId;
    public String caption;
    public boolean photo=false;

    public User(String chatId, int STATS_FOR_PSYCHOLOGY, int STATS_FOR_WORK_OR_HOME, int STATS_FOR_GOVERNMENT, int STATS_FOR_RIGHTS, String questionForPsychology, String questionForGovernment, String questionForRight,String fileId, String userState) {
        this.chatId = chatId;
        this.STATS_FOR_PSYCHOLOGY = STATS_FOR_PSYCHOLOGY;
        this.STATS_FOR_WORK_OR_HOME = STATS_FOR_WORK_OR_HOME;
        this.STATS_FOR_GOVERNMENT = STATS_FOR_GOVERNMENT;
        this.STATS_FOR_RIGHTS = STATS_FOR_RIGHTS;
        this.questionForPsychology = questionForPsychology;
        this.questionForGovernment = questionForGovernment;
        this.questionForRight = questionForRight;
        this.fileId = fileId;
        this.userState = userState;
    }
}
