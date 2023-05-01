package com.example.registrationservice.controller;

import com.example.registrationservice.model.User;
import com.example.registrationservice.model.UserGroup;
import com.example.registrationservice.model.dto.UserGroupDto;
import com.example.registrationservice.model.dto.UsernameForm;
import com.example.registrationservice.repo.UserRepository;
import com.example.registrationservice.service.GroupService;
import com.example.registrationservice.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;

@Controller
public class AccountController {
    private final UserService userService;
    private final GroupService groupService;
    private final UserRepository userRepository;

    public AccountController(UserService userService, GroupService groupService,
                             UserRepository userRepository) {
        this.userService = userService;
        this.groupService = groupService;
        this.userRepository = userRepository;
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        List<UserGroupDto> groupDtoList = groupService.getAllGroupsForUser(userService.getCurrentUsername());
        model.addAttribute("groups", groupDtoList);
        model.addAttribute("currentUserId", userService.getCurrentUserId());
        model.addAttribute("usernameForm", new UsernameForm());
        return "home";
    }

    @GetMapping("/groups/add/{parentId}")
    public String showCreateNewGroupForm(Model model, @PathVariable("parentId") String id) {
        UserGroupDto userGroupDto = new UserGroupDto();
        model.addAttribute("parentId", id);
        model.addAttribute("groupDto", userGroupDto);
        return "groups/add";
    }

    @GetMapping("/groups/add")
    public String showCreateAllNewGroupForm(Model model) {
        UserGroupDto userGroupDto = new UserGroupDto();
        model.addAttribute("parentId", "-1");
        model.addAttribute("groupDto", userGroupDto);
        return "groups/add";
    }

    @PostMapping("/groups/add/{parentId}")
    public String createAllNewGroup(@ModelAttribute("userDto") UserGroupDto userGroupDto,
                                    RedirectAttributes redirectAttributes, @PathVariable("parentId") Long parentId) {

        groupService.createGroup(userService.getCurrentUsername(), userGroupDto, parentId);

        // Перенаправляем пользователя на страницу с подтверждением
        redirectAttributes.addFlashAttribute("message", "Вы успешно зарегистрировались!");
        return "redirect:/home";
    }

    @PostMapping("/groups/{groupId}/addUser")
    public String addUserToGroup(@PathVariable Long groupId, @ModelAttribute("usernameForm") UsernameForm usernameForm) {
        User user;
        try {
            user = userService.loadUserByUsername(usernameForm.getUsername());
        } catch (UsernameNotFoundException e) {
            return "redirect:/home";
        }

        UserGroup group = groupService.getById(groupId).orElse(null);
        if (group == null) {
            return "redirect:/home";
        }
        if(group.getUsers()!=null && group.getUsers().contains(user))
            return "redirect:/home";
        if (group.getSubs() == null) {
            group.setSubs(new HashSet<>());
        }
        group.getSubs().add(user);
        groupService.saveGroup(group);
        return "redirect:/home";
    }

    @PostMapping("/groups/{groupId}/deleteUser/{id}")
    public String deleteUserFromGroup(@PathVariable Long groupId, @PathVariable("id") Long id) {
        User user;
        try {
            user = userService.findUserById(id);
        } catch (UsernameNotFoundException e) {
            return "redirect:/home";
        }

        UserGroup group = groupService.getById(groupId).orElse(null);
        if (group == null) {
            return "redirect:/home";
        }

        if (group.getSubs() != null) {
            group.getSubs().remove(user);
        }
        groupService.saveGroup(group);
        return "redirect:/home";
    }

    @PostMapping("/groups/{groupId}/deleteUserRec/{id}")
    public String deleteUserFromGroupRec(@PathVariable Long groupId, @PathVariable("id") Long id) {
        User user;
        try {
            user = userService.findUserById(id);
        } catch (UsernameNotFoundException e) {
            return "redirect:/home";
        }

        UserGroup group = groupService.getById(groupId).orElse(null);
        if (group == null) {
            return "redirect:/home";
        }
        groupService.deleteRecursion(group, user);
        return "redirect:/home";
    }

    @PostMapping("/groups/{groupId}/addUserRec")
    public String addUserToGroupRec(@PathVariable Long groupId, @ModelAttribute("usernameForm") UsernameForm usernameForm) {
        User user;
        try {
            user = userService.loadUserByUsername(usernameForm.getUsername());
        } catch (UsernameNotFoundException e) {
            return "redirect:/home";
        }

        UserGroup group = groupService.getById(groupId).orElse(null);
        if (group == null) {
            return "redirect:/home";
        }
        if(group.getSubs()!=null && group.getSubs().contains(user))
            return "redirect:/home";
        group.getUsers().add(user);
        while (group.getParentUserGroup() != null) {
            group = group.getParentUserGroup();
            if (group.getUsers() == null) {
                group.setUsers(new HashSet<>());
            }
            group.getUsers().add(user);
        }
        groupService.saveGroup(group);
        return "redirect:/home";
    }

    @GetMapping("/groups/{groupId}/leave")
    public String leaveGroup(@PathVariable Long groupId) {
        User user;
        try {
            user = userService.loadUserByUsername(userService.getCurrentUsername());
        } catch (UsernameNotFoundException e) {
            return "redirect:/home";
        }

        UserGroup group = groupService.getById(groupId).orElse(null);
        if (group == null) {
            return "redirect:/home";
        }
        if (group.getUsers() != null &&  group.getUsers().contains(user)) {
            groupService.deleteRecursion(group, user);
        }
        else if (group.getSubs() != null) {
            group.getSubs().remove(user);
            groupService.saveGroup(group);
        }
        return "redirect:/home";
    }

    @GetMapping("/register-tg/{id}")
    public String register(@PathVariable("id") String id) {
        User user = userService.loadUserByUsername(userService.getCurrentUsername());
        user.setTelegramChatId(id);
        userRepository.save(user);
        return "redirect:/home";
    }


}
