package com.github.twentiethcenturygangsta.adminboard.repository;

import com.github.twentiethcenturygangsta.adminboard.task.Task;
import com.github.twentiethcenturygangsta.adminboard.user.AdminBoardUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByAdminBoardUser(AdminBoardUser adminBoardUser);
}
