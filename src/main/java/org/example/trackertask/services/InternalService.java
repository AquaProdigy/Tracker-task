package org.example.trackertask.services;

import lombok.RequiredArgsConstructor;
import org.example.trackertask.dto.TaskDto;
import org.example.trackertask.dto.TaskSummaryDto;
import org.example.trackertask.dto.UserDailyTaskSummary;
import org.example.trackertask.entities.Task;
import org.example.trackertask.mapper.TaskMapper;
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

    public List<UserDailyTaskSummary> getSummaryTasks() {
        Instant startOfDay = LocalDate.now(ZoneOffset.UTC)
                .atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant endOfDay = startOfDay.plus(1, ChronoUnit.DAYS);

        Map<Long, List<TaskSummaryDto>> completedByUser = taskRepository
                .findCompletedToday(startOfDay, endOfDay)
                .stream()
                .collect(Collectors.groupingBy(
                        Task::getUserId,
                        Collectors.mapping(t -> new TaskSummaryDto(t.getTitle()), Collectors.toList())
                ));

        Map<Long, List<TaskSummaryDto>> inProgressByUser = taskRepository
                .findAllInProcess()
                .stream()
                .collect(Collectors.groupingBy(
                        Task::getUserId,
                        Collectors.mapping(t -> new TaskSummaryDto(t.getTitle()), Collectors.toList())
                ));

        return Stream.concat(completedByUser.keySet().stream(), inProgressByUser.keySet().stream())
                .distinct()
                .map(userId -> new UserDailyTaskSummary(
                        userId,
                        completedByUser.getOrDefault(userId, List.of()),
                        inProgressByUser.getOrDefault(userId, List.of())
                )).toList();

    }
}
