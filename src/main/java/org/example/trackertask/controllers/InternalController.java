package org.example.trackertask.controllers;


import lombok.RequiredArgsConstructor;
import org.example.trackertask.dto.TaskDto;
import org.example.trackertask.services.InternalService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internal/tasks")
public class InternalController {
    private final InternalService internalService;

    @Value("${internal.api-key}")
    private String internalApiKey;

    @GetMapping
    public ResponseEntity<List<TaskDto>> getTasksByUserId(
            @RequestParam Long userId,
            @RequestHeader("X-Internal-Api-Key") String key
    ) {
        if (internalApiKey.equals(key)) {
            return ResponseEntity.ok(internalService.getTasksByUserId(userId));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
