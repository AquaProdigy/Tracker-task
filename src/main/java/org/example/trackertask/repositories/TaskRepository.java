package org.example.trackertask.repositories;

import org.example.trackertask.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByDescriptionAndUserId(String description, Long userId);
    Optional<Task> findByTitleIgnoreCaseAndUserId(String title, Long userId);
    List<Task> findAllByUserId(Long userId);
}
