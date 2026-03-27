package org.example.trackertask.services;

import lombok.RequiredArgsConstructor;
import org.example.trackertask.dto.TaskDto;
import org.example.trackertask.dto.request.TaskRequest;
import org.example.trackertask.entities.Task;
import org.example.trackertask.enums.TaskStatus;
import org.example.trackertask.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public void createTask(TaskRequest taskRequest, Long userId) {
        if (taskRepository.findByTitleIgnoreCaseAndUserId(taskRequest.title(), userId).isPresent()) {
            throw new IllegalArgumentException("Task already exists");
        }

        Task task = Task.builder()
                .title(taskRequest.title())
                .description(taskRequest.description())
                .userId(userId)
                .createdAt(LocalDateTime.now())
                .status(TaskStatus.ONGOING)
                .build();

        taskRepository.save(task);
    }

    public List<TaskDto> getAllTasks(Long userId) {
        return taskRepository.findAllByUserId(userId)
                .stream()
                .map(task -> {
                    return new TaskDto(
                            task.getTitle(),
                            task.getDescription(),
                            task.getStatus(),
                            task.getCreatedAt()
                    );
                }).toList();
    }

}
