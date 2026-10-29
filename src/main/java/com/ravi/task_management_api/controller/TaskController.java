package com.ravi.task_management_api.controller;

import com.ravi.task_management_api.dto.CreateTaskRequest;
import com.ravi.task_management_api.dto.TaskResponse;
import com.ravi.task_management_api.dto.UpdateTaskRequest;
import com.ravi.task_management_api.entity.TaskPriority;
import com.ravi.task_management_api.entity.TaskStatus;
import com.ravi.task_management_api.entity.task;
import com.ravi.task_management_api.exception.ResourceNotFoundException;
import com.ravi.task_management_api.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController 
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService=taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody CreateTaskRequest request){
        TaskResponse createdTask=taskService.createTask(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdTask);
    }

//    @GetMapping
//    public ResponseEntity<List<TaskResponse>> getAllTasks(
//            @RequestParam(required = false)TaskStatus status,
//            @RequestParam(required = false)TaskPriority priority) {
//
//        if (status != null && priority != null) {
//
//            return ResponseEntity.ok(
//                    taskService.getTasksByStatusAndPriority(
//                            status,
//                            priority
//                    )
//            );
//        }
//
//        if(status!=null){
//            return ResponseEntity.ok(
//                    taskService.getTasksByStatus(status));
//        }
//        if (priority != null) {
//            return ResponseEntity.ok(
//                    taskService.getTasksByPriority(priority)
//            );
//        }
//
//        return ResponseEntity.ok(
//                taskService.getAllTasks());
//    }
@GetMapping
public ResponseEntity<List<TaskResponse>> getAllTasks(
        @RequestParam(required = false) TaskStatus status,
        @RequestParam(required = false) TaskPriority priority,
        @RequestParam(required = false) String keyword) {

    return ResponseEntity.ok(
            taskService.searchTasks(status, priority, keyword)
    );
}

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(
            @PathVariable Long id) {

        TaskResponse taskResponse = taskService.getTaskById(id);

        return ResponseEntity.ok(taskResponse);
    }


    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTaskRequest request) {

        TaskResponse updatedTask = taskService.updateTask(id, request);

        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }


}
