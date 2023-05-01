package com.example.registrationservice.scheduler;

import com.example.registrationservice.model.Event;
import com.example.registrationservice.model.EventUser;
import com.example.registrationservice.model.enums.Stage;
import com.example.registrationservice.repo.EventUserRepository;
import com.example.registrationservice.scheduler.email.EmailService;
import com.example.registrationservice.scheduler.telegram.TelegramService;
import com.example.registrationservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class Scheduler {
    private final EventUserRepository eventUserRepository;
    private final EmailService emailService;
    private final UserService userService;
    private final TelegramService telegramService;

    private String url = "http://localhost:8080/events/set-answer/";

    @Scheduled(cron = "0 * * * * *")
    public void performNotify() {
        System.out.println("Begin");
        List<EventUser> eventUsers = eventUserRepository.findAll();
        eventUsers.forEach(e -> {
                    System.out.println(eventUsers);
                    if (e.getNextNotify().isBefore(LocalDateTime.now())) {

//                        if (e.getNotifyStrategy().getFirstCorpEmailSendStage().getValue() <= e.getNextStage().getValue()
//                                && e.getEvent().getIsCorpEmailSend()) {
//                            emailService.sendEmail("dmharitoh@gmail.com", e.getEvent());
//                        }
//
//                        if (e.getNotifyStrategy().getFirstEmailSendStage().getValue() <= e.getNextStage().getValue()
//                                && e.getEvent().getIsEmailSend()) {
//                            emailService.sendEmail("dmharitoh@gmail.com", e.getEvent());
//                        }

                        if (e.getNotifyStrategy().getFirstSmsSendStage().getValue() <= e.getNextStage().getValue()
                                && e.getEvent().getIsSmsSend()) {

                        }

                        if (e.getNotifyStrategy().getFirstTgSendStage().getValue() <= e.getNextStage().getValue()
                                && e.getEvent().getIsTgSend()) {
                            telegramService.sendMessageToUser(e.getEvent().getName() + "\n" + e.getEvent().getDescription() + "\n" + e.getEvent().getTimestamp()
                                            + "\nПодтвердить " + getOkLink(e)+ "\nОтменить " + getCancelLink(e),
                                    e.getUser().getTelegramChatId());

                        }
                        if (e.getNextStage().getValue() + 1 > 3) {
                            e.setNextNotify(null);
                        } else {
                            e.setNextStage(Stage.getByValue(e.getNextStage().getValue() + 1));
                            switch (e.getNextStage()) {
                                case FIRST -> e.setNextNotify(e.getEvent().getTimestamp().minusMinutes(e.getNotifyStrategy().getStageFirstBeforeMinutes()));
                                case SECOND -> e.setNextNotify(e.getEvent().getTimestamp().minusMinutes(e.getNotifyStrategy().getStageSecondBeforeMinutes()));
                                case FINAL -> e.setNextNotify(e.getEvent().getTimestamp().minusMinutes(e.getNotifyStrategy().getStageFinalBeforeMinutes()));
                            }
                            eventUserRepository.save(e);
                        }
                    }
                }
        );
    }

    private String getOkLink(EventUser eu) {
        return url + eu.getEvent().getId() + "/OK" ;
    }

    private String getCancelLink(EventUser eu) {
        return url + eu.getEvent().getId() + "/CANCEL";
    }

}
