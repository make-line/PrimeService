package com.example.primeservice.service;

import com.example.primeservice.model.*;
import com.example.primeservice.model.adapter.Mapper;
import com.example.primeservice.model.dto.EventDto;
import com.example.primeservice.model.enums.Stage;
import com.example.primeservice.model.enums.Status;
import com.example.primeservice.repo.EventRepository;
import com.example.primeservice.repo.EventUserRepository;
import com.example.primeservice.repo.NotifyStrategyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EventService {
    private final UserService userService;
    private final Mapper mapper;
    private final GroupService groupService;
    private final EventRepository eventRepository;
    private final EventUserRepository eventUserRepository;
    private final NotifyStrategyRepository notifyStrategyRepository;

    public Set<Event> getAllCurrentUserEvents(){
        Set<Event> events = new HashSet<>();
        for (EventUser eventUser : userService.loadUserByUsername(userService.getCurrentUsername()).getEventUsers()) {
            events.add(eventUser.getEvent());
        }
        for (UserGroup group : userService.loadUserByUsername(userService.getCurrentUsername()).getGroups()) {
            events.addAll(group.getEvents());
        }
        return events;
    }

    public List<EventDto> getAllOwnerUserEvents(){
        Set<Event> events = eventRepository.findEventsByCreator(userService.getCurrentUsername());
        List<EventDto> eventDtoList = new ArrayList<>();
        events.forEach(e-> {
            EventDto eventDto = mapper.eventToEventDto(e);
            Map<String, Status> userStatusMap = new HashMap<>();
            e.getEventUsers().forEach(eventUser -> userStatusMap.put(eventUser.getUser().getCorpEmail(), eventUser.getStatus()));
            eventDto.setUserStatus(userStatusMap);
            eventDto.setGroupNames(e.getGroups().stream().map(UserGroup::getName).collect(Collectors.toList()));
            eventDtoList.add(eventDto);
        });
        System.out.println(eventDtoList);
        return eventDtoList;
    }

    public void saveEvent(EventDto eventDto){
        Event event = mapper.eventDtoToEvent(eventDto);
        System.out.println(event);
        event.setIsCorpEmailSend(eventDto.getIsCorpEmailSend());
        event.setIsSmsSend(eventDto.getIsSmsSend());
        event.setIsTgSend(eventDto.getIsTgSend());
        event.setIsEmailSend(eventDto.getIsEmailSend());
        event.setEventUsers(new HashSet<>());
        event.setCreator(userService.getCurrentUsername());
        event.setGroups(groupService.getAllGroupsByNames(eventDto.getGroupNames()));
        System.out.println(event);
        Set<EventUser> eventUsers = new HashSet<>();
        LocalDateTime time = LocalDateTime.now();
        for (User user : userService.getAllUsersByNames(eventDto.getUserNames())) {
            EventUser eventUser = new EventUser();
            eventUser.setNotifyStrategy(notifyStrategyRepository.findById(eventDto.getNotificationStrategyId()).orElseThrow());
            eventUser.setNextStage(Stage.CREATE);
            eventUser.setUser(user);
            eventUser.setEvent(event);
            eventUser.setNotifyStrategy(notifyStrategyRepository.findById(1L).get());
            eventUser.setNextNotify(time);
            event.getEventUsers().add(eventUser);
            eventUsers.add(eventUser);
        }
        for (UserGroup group: event.getGroups()){
            for(UserGroup subGroup: groupService.getAllSlaveGroups(group)){
                for (User user: subGroup.getUsers()){
                    EventUser eventUser = new EventUser();
                    eventUser.setNotifyStrategy(notifyStrategyRepository.findById(eventDto.getNotificationStrategyId()).orElseThrow());
                    eventUser.setNextStage(Stage.CREATE);
                    eventUser.setUser(user);
                    eventUser.setEvent(event);
                    eventUser.setNotifyStrategy(notifyStrategyRepository.findById(1L).get());
                    eventUser.setNextNotify(time);
                    event.getEventUsers().add(eventUser);
                    eventUsers.add(eventUser);
                }
            }
            for (User user: group.getUsers()){
                EventUser eventUser = new EventUser();
                eventUser.setNotifyStrategy(notifyStrategyRepository.findById(eventDto.getNotificationStrategyId()).orElseThrow());
                eventUser.setNextStage(Stage.CREATE);
                eventUser.setUser(user);
                eventUser.setEvent(event);
                eventUser.setNotifyStrategy(notifyStrategyRepository.findById(1L).get());
                eventUser.setNextNotify(time);
                event.getEventUsers().add(eventUser);
                eventUsers.add(eventUser);
            }
        }

        eventRepository.save(event);
        eventUserRepository.saveAll(eventUsers);
        System.out.println(event);
    }

    public void setAnswerStatus(Status status, Long eventId){
        User user = userService.loadUserByUsername(userService.getCurrentUsername());
        Optional<Event> event = eventRepository.findById(eventId);
        if(event.isPresent()){
            EventUser eventUser = eventUserRepository.getEventUserByUserAndEvent(user, event.get());
            eventUser.setStatus(status);
            eventUserRepository.save(eventUser);
        }
    }
}
