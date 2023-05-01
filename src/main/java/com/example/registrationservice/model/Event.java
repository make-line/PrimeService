package com.example.registrationservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    private LocalDateTime timestamp;
    @ManyToMany
    private List<UserGroup> groups;
    @OneToMany(mappedBy = "event")
    private Set<EventUser> eventUsers;
    private String creator;
    private Boolean isCorpEmailSend;
    private Boolean isEmailSend;
    private Boolean isSmsSend;
    private Boolean isTgSend;

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", timestamp=" + timestamp +
                ", creator='" + creator + '\'' +
                ", isCorpEmailSend=" + isCorpEmailSend +
                ", isEmailSend=" + isEmailSend +
                ", isSmsSend=" + isSmsSend +
                ", isTgSend=" + isTgSend +
                '}';
    }
}
