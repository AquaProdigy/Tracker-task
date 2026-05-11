package org.example.trackertask.services;

import lombok.RequiredArgsConstructor;
import org.example.trackertask.dto.response.TaskSummaryResponse;
import org.example.trackertask.dto.response.UserDailyTaskSummaryResponse;
import org.example.trackertask.entities.Task;
import org.example.trackertask.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class InternalService {
    private final TaskRepository taskRepository;

    public List<UserDailyTaskSummaryResponse> getSummaryTasks() {
        Instant startOfDay = LocalDate.now(ZoneOffset.UTC)
                .atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant endOfDay = startOfDay.plus(1, ChronoUnit.DAYS);

        Map<Long, List<TaskSummaryResponse>> completedByUser = taskRepository
                .findCompletedToday(startOfDay, endOfDay)
                .stream()
                .collect(Collectors.groupingBy(
                        Task::getUserId,
                        Collectors.mapping(t -> new TaskSummaryResponse(t.getTitle()), Collectors.toList())
                ));

        Map<Long, List<TaskSummaryResponse>> inProgressByUser = taskRepository
                .findAllInProcess()
                .stream()
                .collect(Collectors.groupingBy(
                        Task::getUserId,
                        Collectors.mapping(t -> new TaskSummaryResponse(t.getTitle()), Collectors.toList())
                ));

        return Stream.concat(completedByUser.keySet().stream(), inProgressByUser.keySet().stream())
                .distinct()
                .map(userId -> new UserDailyTaskSummaryResponse(
                        userId,
                        completedByUser.getOrDefault(userId, List.of()),
                        inProgressByUser.getOrDefault(userId, List.of())
                )).toList();

    }
}
