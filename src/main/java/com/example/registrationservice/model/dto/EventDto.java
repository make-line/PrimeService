package com.example.registrationservice.model.dto;

import com.example.registrationservice.model.EventUser;
import com.example.registrationservice.model.UserGroup;
import com.example.registrationservice.model.enums.Status;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
