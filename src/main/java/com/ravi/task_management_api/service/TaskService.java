package com.ravi.task_management_api.service;

import com.ravi.task_management_api.dto.CreateTaskRequest;
import com.ravi.task_management_api.dto.TaskResponse;
import com.ravi.task_management_api.dto.UpdateTaskRequest;
import com.ravi.task_management_api.entity.TaskPriority;
import com.ravi.task_management_api.entity.TaskStatus;
import com.ravi.task_management_api.entity.task;
import com.ravi.task_management_api.exception.ResourceNotFoundException;
import com.ravi.task_management_api.repository.TaskRepository;
import org.apache.tomcat.util.http.parser.Priority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository=taskRepository;
    }

    public TaskResponse createTask(CreateTaskRequest request) {

        task newTask = new task();

        newTask.setTitle(request.getTitle());
        newTask.setDescription(request.getDescription());
        newTask.setStatus(request.getStatus());
        newTask.setPriority(request.getPriority());
        newTask.setDueDate(request.getDueDate());

        task savedTask = taskRepository.save(newTask);

        return convertToResponse(savedTask);
    }


    public List<TaskResponse> getAllTasks() {

        return taskRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public TaskResponse getTaskById(Long id) {

        task existingTask = taskRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task not found with id: " + id
                        )
                );

        return convertToResponse(existingTask);
    }

    public TaskResponse updateTask(
            Long id,
            UpdateTaskRequest request) {

        task existingTask = taskRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task not found with id: " + id
                        )
                );

        existingTask.setTitle(request.getTitle());
        existingTask.setDescription(request.getDescription());
        existingTask.setStatus(request.getStatus());
        existingTask.setPriority(request.getPriority());
        existingTask.setDueDate(request.getDueDate());

        task updatedTask = taskRepository.save(existingTask);

        return convertToResponse(updatedTask);
    }
    public void deleteTask(Long id) {
        task t1=taskRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task not found with id: " + id
                        )
                );
        taskRepository.deleteById(id);
    }

    public List<TaskResponse> getTasksByStatus(TaskStatus status){
        return taskRepository.findByStatus(status)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public List<TaskResponse> getTasksByPriority(TaskPriority priority){
        return taskRepository.findByPriority(priority)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public List<TaskResponse> getTasksByStatusAndPriority(TaskStatus status, TaskPriority priority){
        return taskRepository.findByStatusAndPriority(status,priority)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public List<TaskResponse> searchTasks(String keyword) {

        return taskRepository
                .findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                        keyword,
                        keyword
                )
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public List<TaskResponse> searchTasks(
            TaskStatus status,
            TaskPriority priority,
            String keyword) {

        if (keyword != null && keyword.isBlank()) {
            keyword = null;
        }

        return taskRepository
                .searchTasks(status, priority, keyword)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    private TaskResponse convertToResponse(task task) {

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }


}
