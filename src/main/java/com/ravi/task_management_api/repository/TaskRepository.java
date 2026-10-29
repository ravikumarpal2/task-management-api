package com.ravi.task_management_api.repository;

import com.ravi.task_management_api.entity.TaskPriority;
import com.ravi.task_management_api.entity.TaskStatus;
import com.ravi.task_management_api.entity.task;
import org.apache.tomcat.util.http.parser.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<task,Long> {
    List<task>findByStatus(TaskStatus status);

    List<task> findByPriority(TaskPriority priority);

    List<task> findByStatusAndPriority(TaskStatus status, TaskPriority priority);

    List<task> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String titleKeyword,
            String descriptionKeyword
    );

    @Query("""
        SELECT t FROM task t
        WHERE (:status IS NULL OR t.status = :status)
        AND (:priority IS NULL OR t.priority = :priority)
        AND (
            :keyword IS NULL
            OR LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
        """)
    List<task> searchTasks(
            @Param("status") TaskStatus status,
            @Param("priority") TaskPriority priority,
            @Param("keyword") String keyword
    );
}
