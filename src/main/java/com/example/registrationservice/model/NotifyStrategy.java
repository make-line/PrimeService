package com.example.registrationservice.model;

import com.example.registrationservice.model.enums.Stage;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.EnumSet;

@Entity
@Getter
public class NotifyStrategy {

    @Id
    private Long id;
    private String name;

    private String creator;
    private Stage firstCorpEmailSendStage;
    private Stage firstEmailSendStage;
    private Stage firstTgSendStage;
    private Stage firstSmsSendStage;

    private int stageFirstBeforeMinutes;
    private int stageSecondBeforeMinutes;
    private int stageFinalBeforeMinutes;

    private Stage hardModeActivationStage;

    private int everyMinutesHardMode;

}
