package org.example.trackertask.repositories;

import org.example.trackertask.entities.Task;
import org.example.trackertask.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByTitleIgnoreCaseAndUserId(String title, Long userId);
    List<Task> findAllByUserId(Long userId);
    List<Task> findAllByUserIdAndStatus(Long userId, TaskStatus status);
    Optional<Task> findByIdAndUserId(Long id, Long userId);

    @Query("""
SELECT t FROM Task t 
WHERE t.createdAt >= :startOfDay
    AND t.createdAt < :endOfDay
    AND t.status == "COMPLETED"

""")
    List<Task> findCompletedToday(
            @Param("startOfDay") Instant startOfDay,
            @Param("endOfDay") Instant endOfDay
    );

    @Query("SELECT t FROM Task t where t.status == 'IN_PROCESS'")
    List<Task> findAllInProcess();
}
