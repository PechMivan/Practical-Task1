package com.todo.todo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TaskServiceTests {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTasks() {
        // Arrange
        when(taskRepository.findAll()).thenReturn(Arrays.asList(
                new Task(1L, "Task 1", "Description 1", false),
                new Task(2L, "Task 2", "Description 2", true)
        ));

        // Act
        List<Task> tasks = taskService.getAllTasks();

        // Assert
        assertEquals(2, tasks.size());
        // Add more assertions based on your business logic
    }

    @Test
    public void testGetTaskById() {
        // Arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.of(new Task(1L, "Task 1", "Description 1", false)));

        // Act
        Optional<Task> task = taskService.getTaskById(1L);

        // Assert
        assertTrue(task.isPresent());
        assertEquals("Task 1", task.get().getTitle());
        // Add more assertions based on your business logic
    }

    @Test
    public void testCreateTask() {
        // Arrange
        Task newTask = new Task(1L,"New Task", "New Description", false);
        when(taskRepository.save(any(Task.class))).thenReturn(new Task(1L, "New Task", "New Description", false));

        // Act
        Task createdTask = taskService.createTask(newTask);

        // Assert
        assertNotNull(createdTask);
        assertEquals("New Task", createdTask.getTitle());
        // Add more assertions based on your business logic
    }

    @Test
    public void testUpdateTask_Success() {
        // Arrange
        Long taskId = 1L;
        Task existingTaskInDb = new Task(1L, "Old Title", "Old Description", false);
        Task updatedTask = new Task(1L, "New Title", "New Description", true);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTaskInDb));
        when(taskRepository.save(Mockito.any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Task result = taskService.updateTask(taskId, updatedTask);

        // Assert
        assertEquals(updatedTask.getTitle(), result.getTitle());
        assertEquals(updatedTask.getDescription(), result.getDescription());
        assertEquals(updatedTask.isCompleted(), result.isCompleted());
    }

    @Test
    public void testDeleteTask() {
        // Arrange
        doNothing().when(taskRepository).deleteById(1L);

        // Act
        taskService.deleteTask(1L);

        // Assert
        verify(taskRepository, times(1)).deleteById(1L);
        // Add more assertions based on your business logic
    }
}
