package com.example.registrationservice.service;

import com.example.registrationservice.model.User;
import com.example.registrationservice.model.UserGroup;
import com.example.registrationservice.model.adapter.Mapper;
import com.example.registrationservice.model.dto.UserGroupDto;
import com.example.registrationservice.repo.GroupRepository;
import com.example.registrationservice.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final Mapper mapper;

    public GroupService(GroupRepository groupRepository, UserRepository userRepository, Mapper mapper) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public void createGroup(String currentUserName, UserGroupDto userGroupDto, Long parentGroupId) {
        User user = userRepository.findByCorpEmail(currentUserName);
        UserGroup group = mapper.groupDtoToGroup(userGroupDto);
        group.setAdmin(user);
        Set<User> users = new HashSet<>();
        users.add(user);
        group.setUsers(users);
        groupRepository.findById(parentGroupId).ifPresent(group::setParentUserGroup);
        groupRepository.save(group);
    }

    public List<UserGroupDto> getAllGroupsForUser(String currentUserName) {
        return groupRepository.
                findAllVisibleGroupsForUser(userRepository.findByCorpEmail(currentUserName).getId())
                .stream().map(mapper::groupToDtoGroup).toList();

    }

    public Optional<UserGroup> getById(Long id) {
        return groupRepository.findById(id);
    }

    public void saveGroup(UserGroup group) {
        groupRepository.save(group);
    }

    public void deleteRecursion(UserGroup userGroup, User user) {
        if (userGroup.getUsers() != null) {
            userGroup.getUsers().remove(user);
            groupRepository.save(userGroup);
        }
        List<UserGroup> groupList = groupRepository.findAllSlaveGroups(userGroup.getId());
        for (UserGroup group : groupList) {
            System.out.println(group);
            deleteRecursion(group, user);
            if (group.getUsers() != null) {
                group.getUsers().remove(user);
                groupRepository.save(group);
            }
        }
    }

    public List<UserGroup> getAllGroupsByNames(List<String> names){
        List<UserGroup> groups = new ArrayList<>();
        for (String name: names) {
            groups.add(groupRepository.findByName(name));
        }
        return groups;
    }


}
