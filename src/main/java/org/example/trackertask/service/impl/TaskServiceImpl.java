package org.example.trackertask.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.trackertask.api.ApiMessages;
import org.example.trackertask.dto.response.TaskResponse;
import org.example.trackertask.dto.request.TaskRequest;
import org.example.trackertask.dto.request.TaskUpdateRequest;
import org.example.trackertask.entity.Task;
import org.example.trackertask.model.TaskStatus;
import org.example.trackertask.exception.TaskAlreadyExistsException;
import org.example.trackertask.exception.TaskNotFoundException;
import org.example.trackertask.mapper.TaskMapper;
import org.example.trackertask.repository.TaskRepository;
import org.example.trackertask.service.TaskService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Transactional
    public void createTask(TaskRequest taskRequest, Long userId) {
        Task task = taskMapper.toEntity(taskRequest);
        task.setUserId(userId);

        try {
            taskRepository.save(task);
            log.info("Created task with id {} - title - {}", task.getId(), task.getTitle());
        } catch (DataIntegrityViolationException ex) {
            throw new TaskAlreadyExistsException(ApiMessages.TASK_ALREADY_EXISTS.getMessage());
        }
    }

    public List<TaskResponse> getAllTasks(Long userId, TaskStatus status) {
        List<Task> tasks = taskRepository.findAllByUserIdAndStatus(userId, status);
        log.info("Found {} tasks for status {}", tasks.size(), status);
        return tasks.stream()
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
