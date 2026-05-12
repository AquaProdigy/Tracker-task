package org.example.trackertask.controller;

import org.example.trackertask.dto.response.UserDailyTaskSummaryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface InternalController {
    @GetMapping
    ResponseEntity<List<UserDailyTaskSummaryResponse>> getDailySummery();
}
