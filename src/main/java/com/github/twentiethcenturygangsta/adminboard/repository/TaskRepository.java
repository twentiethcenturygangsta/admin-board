package com.github.twentiethcenturygangsta.adminboard.repository;

import com.github.twentiethcenturygangsta.adminboard.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
