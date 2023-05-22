package com.github.twentiethcenturygangsta.adminboard.repository;

import com.github.twentiethcenturygangsta.adminboard.entity.AdminBoardUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminBoardUserRepository extends JpaRepository<AdminBoardUser, Long> {
}
