package com.example.registrationservice.controller;

import com.example.registrationservice.model.Event;
import com.example.registrationservice.model.dto.EventDto;
import com.example.registrationservice.model.dto.UserGroupDto;
import com.example.registrationservice.model.enums.Status;
import com.example.registrationservice.service.EventService;
import com.example.registrationservice.service.GroupService;
import com.example.registrationservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final GroupService groupService;
    private final UserService userService;

    @GetMapping
    public String showEventPage(Model model) {
        Set<Event> events = eventService.getAllCurrentUserEvents();
        model.addAttribute("events", events);
        return "event/get";
    }

    @GetMapping("/add")
    public String showAddEventPage(Model model) {
        EventDto event = new EventDto();
        List<String> allGroups = groupService.getAllGroupsForUser(userService.getCurrentUsername()).stream().map(UserGroupDto::getName).collect(Collectors.toList());
        model.addAttribute("event", event);
        model.addAttribute("allGroups", allGroups);
        model.addAttribute("allUsers", userService.getAllUserNames());
        return "event/add";
    }

    @PostMapping("/add")
    public String addEvent(@ModelAttribute("event") EventDto event) {
        System.out.println(event);
        eventService.saveEvent(event);
        return "redirect:/events";
    }

    @GetMapping("/owner")
    public String showOwnerEventPage(Model model) {
        List<EventDto> events = eventService.getAllOwnerUserEvents();
        model.addAttribute("events", events);
        return "event/owner";
    }

    @GetMapping("/set-answer/{id}/{status}")
    public String setAnswer(@PathVariable Long id, @PathVariable Status status) {
        eventService.setAnswerStatus(status, id);
        return "redirect:/events";

    }
}
