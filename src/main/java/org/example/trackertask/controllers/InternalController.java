package org.example.trackertask.controllers;


import lombok.RequiredArgsConstructor;
import org.example.trackertask.dto.TaskDto;
import org.example.trackertask.mapper.TaskMapper;
import org.example.trackertask.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internal/tasks")
public class InternalController {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Value("${internal.api-key}")
    private String internalApiKey;

    @GetMapping
    public ResponseEntity<List<TaskDto>> getTasksByUserId(
            @RequestParam Long userId,
            @RequestHeader("X-Internal-Api-Key") String key
    ) {
        if (internalApiKey.equals(key)) {
            return ResponseEntity.ok(taskRepository.findAllByUserId(userId)
                    .stream()
                    .map(taskMapper::toDto)
                    .toList()
            );
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
