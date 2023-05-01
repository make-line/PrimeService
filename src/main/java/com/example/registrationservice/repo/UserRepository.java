package com.example.registrationservice.repo;

import com.example.registrationservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByCorpEmail(String corpEmail);
    Optional<User> findByTelegramChatId(String chatId);

}