package org.example.trackertask.service;

import org.example.trackertask.dto.response.UserDailyTaskSummaryResponse;

import java.util.List;

public interface InternalService {
    List<UserDailyTaskSummaryResponse> getSummaryTasks();
}
