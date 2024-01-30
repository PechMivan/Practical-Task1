package com.todo.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TaskService {

    public List<Task> getAllTasks();


    public Optional<Task> getTaskById(Long taskId);

    public Task createTask(Task task);

    public Task updateTask(Long taskId, Task updatedTask);

    public void deleteTask(Long taskId);

}

