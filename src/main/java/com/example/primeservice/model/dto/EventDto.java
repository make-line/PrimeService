package com.example.primeservice.model.dto;

import com.example.primeservice.model.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class EventDto {
    private Long id;
    private String name;
    private String description;

    private LocalDateTime timestamp;

    private List<String> groupNames;
    private String[] userNames;

    private String creator;

    private Map<String, Status> userStatus;

    private Boolean isCorpEmailSend;
    private Boolean isEmailSend;
    private Boolean isSmsSend;
    private Boolean isTgSend;

    private Long notificationStrategyId;

//    public boolean getIsCorpEmailSend() {
//        return isCorpEmailSend;
//    }
//
//    public void setIsCorpEmailSend(boolean corpEmailSend) {
//        isCorpEmailSend = corpEmailSend;
//    }
//
//    public boolean getIsEmailSend() {
//        return isEmailSend;
//    }
//
//    public void setIsEmailSend(boolean emailSend) {
//        isEmailSend = emailSend;
//    }
//
//    public boolean getIsSmsSend() {
//        return isSmsSend;
//    }
//
//    public void setIsSmsSend(boolean smsSend) {
//        isSmsSend = smsSend;
//    }
//
//    public boolean getIsTgSend() {
//        return isTgSend;
//    }
//
//    public void setIsTgSend(boolean tgSend) {
//        isTgSend = tgSend;
//    }

}
