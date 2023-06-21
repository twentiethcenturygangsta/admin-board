package com.github.twentiethcenturygangsta.adminboard.repository;

import com.github.twentiethcenturygangsta.adminboard.user.AdminBoardUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminBoardUserRepository extends JpaRepository<AdminBoardUser, Long> {
    Optional<AdminBoardUser> findByUserIdAndPassword(String userId, String password);
    Optional<AdminBoardUser> findByUserId(String userId);
}
