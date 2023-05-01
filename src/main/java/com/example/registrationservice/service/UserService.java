package com.example.registrationservice.service;

import com.example.registrationservice.model.User;
import com.example.registrationservice.model.adapter.Mapper;
import com.example.registrationservice.model.dto.RegistrationDto;
import com.example.registrationservice.repo.GroupRepository;
import com.example.registrationservice.repo.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService  implements UserDetailsService {
    private final UserRepository userRepository;
    private final Mapper mapper;
    private final GroupRepository groupRepository;

    public UserService(UserRepository userRepository, Mapper mapper,
                       GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.groupRepository = groupRepository;
    }

//    public User getUserByEmail(String corpEmail) {
//        return userRepository.findByCorpEmail(corpEmail);
//    }

    public void registerNewUser(RegistrationDto userDto) {
         User user = mapper.userRegistrationDtoToUser(userDto);
         userRepository.save(user);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByCorpEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public User findUserById(Long id) throws UsernameNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
    public Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return  userRepository.findByCorpEmail(auth.getName()).getId();
    }
    public List<String> getAllUserNames(){
        return userRepository.findAll().stream().map(User::getCorpEmail).collect(Collectors.toList());
    }

    public List<User> getAllUsersByNames(String... names){
        List<User> users = new ArrayList<>();
        for (String name: names) {
            users.add(userRepository.findByCorpEmail(name));
        }
        return users;
    }



}
