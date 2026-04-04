package org.example.trackertask.services;

import lombok.RequiredArgsConstructor;
import org.example.trackertask.api.ApiMessages;
import org.example.trackertask.dto.TaskDto;
import org.example.trackertask.dto.request.TaskRequest;
import org.example.trackertask.dto.request.TaskUpdateRequest;
import org.example.trackertask.entities.Task;
import org.example.trackertask.enums.TaskStatus;
import org.example.trackertask.exceptions.TaskAlreadyExistsException;
import org.example.trackertask.exceptions.TaskNotFoundException;
import org.example.trackertask.mapper.TaskMapper;
import org.example.trackertask.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public void createTask(TaskRequest taskRequest, Long userId) {
        if (taskRepository.findByTitleIgnoreCaseAndUserId(taskRequest.title(), userId).isPresent()) {
            throw new TaskAlreadyExistsException(ApiMessages.TASK_ALREADY_EXISTS.getMessage());
        }

        Task task = taskMapper.toEntity(taskRequest);
        task.setUserId(userId);

        taskRepository.save(task);
    }

    public List<TaskDto> getAllTasks(Long userId, TaskStatus status) {
        if (status != null) {
            return taskRepository.findAllByUserIdAndStatus(userId, status)
                    .stream()
                    .map(taskMapper::toDto)
                    .toList();
        }
        return taskRepository.findAllByUserId(userId)
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }

    public void deleteTask(Long taskId, Long userId) {
        Task task = taskRepository.findByIdAndUserId(taskId, userId).orElseThrow(
                () -> new TaskNotFoundException(ApiMessages.TASK_NOT_FOUND.getMessage())
        );

        taskRepository.delete(task);
    }

    public void updateTask(TaskUpdateRequest taskUpdateRequest, Long taskId, Long userId) {
        Task task = taskRepository.findByIdAndUserId(taskId, userId).orElseThrow(
                () -> new TaskNotFoundException(ApiMessages.TASK_NOT_FOUND.getMessage())
        );

        taskMapper.updateEntity(taskUpdateRequest, task);
        taskRepository.save(task);
    }

}
