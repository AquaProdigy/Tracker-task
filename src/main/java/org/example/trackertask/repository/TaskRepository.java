package org.example.trackertask.repository;

import org.example.trackertask.entity.Task;
import org.example.trackertask.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByUserId(Long userId);
    Optional<Task> findByIdAndUserId(Long id, Long userId);

    @Query("""
SELECT t FROM Task t
WHERE t.userId = :userId
and (:status is null or t.status = :status)
""")
    List<Task> findAllByUserIdAndStatus(Long userId, TaskStatus status);

    @Query("""
SELECT t FROM Task t 
WHERE t.createdAt >= :startOfDay
    AND t.createdAt < :endOfDay
    AND t.status = 'COMPLETED'

""")
    List<Task> findCompletedToday(
            @Param("startOfDay") Instant startOfDay,
            @Param("endOfDay") Instant endOfDay
    );

    @Query("SELECT t FROM Task t where t.status = 'IN_PROGRESS'")
    List<Task> findAllInProcess();
}
