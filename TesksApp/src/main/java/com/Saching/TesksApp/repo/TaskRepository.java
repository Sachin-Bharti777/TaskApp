package com.Saching.TesksApp.repo;

import com.Saching.TesksApp.entity.Task;
import com.Saching.TesksApp.entity.User;
import com.Saching.TesksApp.enums.Priority;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task , Long> {


    List<Task> findByUser(User user, Sort sort);
    List<Task> findByCompletedAndUser(boolean completed, User user);
    List<Task> findByPriorityAndUser(Priority priority, User user, Sort sort);
}
