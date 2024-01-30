package com.todo.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskServiceImpl taskService;

    @GetMapping
    public String getHomePage(Model model) {
        List<Task> taskList = taskService.getAllTasks();
        model.addAttribute("tasks", taskList);
        return "index";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskService.getTaskById(id);

        return task.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/create")
    public String createTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "CreateForm";
    }

    @PostMapping
    public String createTask(@ModelAttribute Task task) {
        Task createdTask = taskService.createTask(task);
        return "redirect:/api/tasks";
    }

    @GetMapping("/{id}/update")
    public String updateTaskForm(@PathVariable Long id, Model model) {
        Optional<Task> task = taskService.getTaskById(id);

        task.ifPresent(value -> model.addAttribute("task", value));

        return task.map(t -> "updateForm").orElse("taskNotFound");
    }

    @PostMapping("/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute Task updatedTask) {
        taskService.updateTask(id, updatedTask);
        return "redirect:/api/tasks";
    }

    @GetMapping("/{id}/delete")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/api/tasks";
    }
}

