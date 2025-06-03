package com.Saching.TesksApp.service.impl;

import com.Saching.TesksApp.dto.Response;
import com.Saching.TesksApp.dto.TaskRequest;
import com.Saching.TesksApp.entity.Task;
import com.Saching.TesksApp.entity.User;
import com.Saching.TesksApp.enums.Priority;
import com.Saching.TesksApp.exception.NotFoundException;
import com.Saching.TesksApp.repo.TaskRepository;
import com.Saching.TesksApp.service.TaskService;
import com.Saching.TesksApp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    @Override
    public Response<Task> createTask(TaskRequest taskRequest) {
       log.info("INSIDE createdTask()");

        User user = userService.getCurrentLoggedUser();
        Task taskToSave = Task.builder()
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .completed(taskRequest.getCompleted())
                .priority(taskRequest.getPriority())
                .dueDate(taskRequest.getDueDate())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .user(user)
                .build();

        Task savedTask = taskRepository.save(taskToSave);

        return Response.<Task>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Task Created Successfully")
                .data(savedTask)
                .build();
    }

    @Override
    @Transactional
    public Response<List<Task>> getAllMyTasks() {
      log.info("Inside getAllMyTasks");
      User currentUser = userService.getCurrentLoggedUser();

      List<Task> tasks = taskRepository.findByUser(currentUser, Sort.by(Sort.Direction.DESC,"id"));
      return Response.<List<Task>>builder()
              .statusCode(HttpStatus.OK.value())
              .message("Tasks retrieved Successfully")
              .data(tasks)
              .build();
    }

    @Override
    public Response<Task> getTaskById(Long id) {
       log.info("Inside getTaskById");

       Task task = taskRepository.findById(id)
               .orElseThrow(()-> new NotFoundException("Task not found"));
       return Response.<Task>builder()
               .statusCode(HttpStatus.OK.value())
               .message("Tasks retrieved Successfully")
               .data(task)
               .build();
    }

    @Override
    public Response<Task> updatedTask(TaskRequest taskRequest) {
        log.info("Inside updatedTask");

        Task task = taskRepository.findById(taskRequest.getId())
                .orElseThrow(()-> new NotFoundException("Task not found"));

        if(taskRequest.getTitle() != null) task.setTitle(taskRequest.getTitle());
        if(taskRequest.getDescription() != null) task.setDescription(taskRequest.getDescription());
        if(taskRequest.getCompleted() != null) task.setCompleted(taskRequest.getCompleted());
        if(taskRequest.getPriority() != null) task.setPriority(taskRequest.getPriority());
        if(taskRequest.getDueDate() != null) task.setDueDate(taskRequest.getDueDate());
        task.setUpdatedAt(LocalDateTime.now());


        // update the task in the database

        Task updatedTask = taskRepository.save(task);

        return  Response.<Task>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Tasks updated Successfully")
                .data(updatedTask)
                .build();
    }

    @Override
    public Response<Void> deleteTask(Long id) {
       log.info("inside delete task");
       if(!taskRepository.existsById(id)){
           throw new NotFoundException("Task does not exists");
       }
       taskRepository.deleteById(id);

       return Response.<Void>builder()
               .statusCode(HttpStatus.OK.value())
               .message("task deleted successfully")
               .build();
    }

    @Override
    @Transactional
    public Response<List<Task>> getMyTasksByCompletionStatus(boolean completed) {
        log.info("inside getMyTasksByCompletionStatus");

        User currentUser = userService.getCurrentLoggedUser();

        List<Task> tasks = taskRepository.findByCompletedAndUser(completed, currentUser);

      return Response.<List<Task>>builder()
              .statusCode(HttpStatus.OK.value())
              .message("Tasks filtered by completion status for user")
              .data(tasks)
              .build();

    }

    @Override
    public Response<List<Task>> getMyTasksByPriority(String priority) {
        log.info("inside getMyTasksByPriority");
        User currentUser = userService.getCurrentLoggedUser();

        Priority priorityEnum = Priority.valueOf(priority.toUpperCase());

        List<Task> tasks = taskRepository.findByPriorityAndUser
                (priorityEnum, currentUser, Sort.by(Sort.Direction.DESC,"id"));

        return Response.<List<Task>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Tasks filtered by priority for user")
                .data(tasks)
                .build();


    }
}
