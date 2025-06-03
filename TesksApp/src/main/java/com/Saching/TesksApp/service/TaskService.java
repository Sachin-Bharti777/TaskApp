package com.Saching.TesksApp.service;

import com.Saching.TesksApp.dto.Response;
import com.Saching.TesksApp.dto.TaskRequest;
import com.Saching.TesksApp.entity.Task;

import java.util.List;

public interface TaskService {

    Response<Task> createTask(TaskRequest taskRequest);
    Response<List<Task>> getAllMyTasks();
    Response<Task> getTaskById(Long id);
    Response<Task> updatedTask(TaskRequest taskRequest);
    Response<Void> deleteTask(Long id);
    Response<List<Task>> getMyTasksByCompletionStatus(boolean completed);
    Response<List<Task>> getMyTasksByPriority(String priority);



}
