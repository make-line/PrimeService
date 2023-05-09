package com.example.primeservice.repo;

import com.example.primeservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByCorpEmail(String corpEmail);
    Optional<User> findByTelegramChatId(String chatId);

}