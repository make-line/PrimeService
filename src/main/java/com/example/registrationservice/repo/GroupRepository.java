package com.example.registrationservice.repo;

import com.example.registrationservice.model.User;
import com.example.registrationservice.model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GroupRepository extends JpaRepository<UserGroup, Long> {
    @Query(value = "SELECT g FROM UserGroup g LEFT JOIN g.subs s LEFT JOIN g.users u WHERE u.id = :userId OR s.id = :userId ORDER BY CASE WHEN g.parentUserGroup.id IS NULL THEN 0 ELSE 1 END, g.parentUserGroup.id")
    List<UserGroup> findAllVisibleGroupsForUser(@Param("userId") Long userId);
    @Query(value = "SELECT g FROM UserGroup g WHERE g.parentUserGroup.id = :id")
    List<UserGroup> findAllSlaveGroups(@Param("id") Long id);

    UserGroup findByName(String name);
}