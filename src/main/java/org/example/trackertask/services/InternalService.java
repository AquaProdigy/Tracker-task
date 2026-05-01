package org.example.trackertask.services;

import lombok.RequiredArgsConstructor;
import org.example.trackertask.dto.TaskDto;
import org.example.trackertask.mapper.TaskMapper;
import org.example.trackertask.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InternalService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public List<TaskDto> getTasksByUserId(Long userId) {
        return taskRepository.findAllByUserId(userId)
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }
}
