package org.example.trackertask.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public void createTask(TaskRequest taskRequest, Long userId) {
        if (taskRepository.findByTitleIgnoreCaseAndUserId(taskRequest.title(), userId).isPresent()) {
            throw new TaskAlreadyExistsException(ApiMessages.TASK_ALREADY_EXISTS.getMessage());
        }
        log.info("Creating task {}", taskRequest.title());

        Task task = taskMapper.toEntity(taskRequest);
        task.setUserId(userId);

        taskRepository.save(task);
    }

    public List<TaskDto> getAllTasks(Long userId, TaskStatus status) {
        if (status != null) {
            log.info("Getting all tasks for status {}", status);
            return taskRepository.findAllByUserIdAndStatus(userId, status)
                    .stream()
                    .map(taskMapper::toDto)
                    .toList();
        }

        log.info("Getting all tasks without status");
        return taskRepository.findAllByUserId(userId)
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @Transactional
    public void deleteTask(Long taskId, Long userId) {
        Task task = taskRepository.findByIdAndUserId(taskId, userId).orElseThrow(
                () -> new TaskNotFoundException(ApiMessages.TASK_NOT_FOUND.getMessage())
        );
        log.info("Deleting task {}", taskId);

        taskRepository.delete(task);
    }

    @Transactional
    public void updateTask(TaskUpdateRequest taskUpdateRequest, Long taskId, Long userId) {
        Task task = taskRepository.findByIdAndUserId(taskId, userId).orElseThrow(
                () -> new TaskNotFoundException(ApiMessages.TASK_NOT_FOUND.getMessage())
        );

        log.info("Updating task {}", taskId);

        taskMapper.updateEntity(taskUpdateRequest, task);
        taskRepository.save(task);
    }

}
