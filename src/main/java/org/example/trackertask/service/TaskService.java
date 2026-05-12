package org.example.trackertask.service;

import org.example.trackertask.dto.request.TaskRequest;
import org.example.trackertask.dto.request.TaskUpdateRequest;
import org.example.trackertask.dto.response.TaskResponse;
import org.example.trackertask.model.TaskStatus;

import java.util.List;

public interface TaskService {
    void createTask(TaskRequest taskRequest, Long userId);
    List<TaskResponse> getAllTasks(Long userId, TaskStatus status);
    void deleteTask(Long taskId, Long userId);
    void updateTask(TaskUpdateRequest taskUpdateRequest, Long taskId, Long userId);
}
