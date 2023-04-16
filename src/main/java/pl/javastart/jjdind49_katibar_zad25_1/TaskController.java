package pl.javastart.jjdind49_katibar_zad25_1;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
    class TaskController {
        private final TaskRepository taskRepository;
        private final TaskService taskService;

        public TaskController(TaskRepository taskRepository, TaskService taskService) {
            this.taskRepository = taskRepository;
            this.taskService = taskService;
        }

        @GetMapping("/home")
        String home() {
            return "home";
        }

        @GetMapping("/")
        String goHome() {
            return "home";
        }

        @GetMapping("/showToDo")
        public String showToDoList(Model model) {
            List<Task> allNotFinishedTasks = getAllNotFinishedTasks();
            model.addAttribute("toDoTasks", allNotFinishedTasks);

            return "showToDo";
        }

    @GetMapping("/showArchives")
    public String showArchivesList(Model model) {
            List<Task> allCompletedTasks = getAllCompletedTasks();
            model.addAttribute("archives", allCompletedTasks);

            return "showArchives";
    }

        @GetMapping("/addTask")
        public String addTask(Model model) {
            Category[] categories = Category.values();
            model.addAttribute("categories", categories);

            return "addTask";
        }

        @PostMapping("/addTask")
        public String addTaskForm(@RequestParam(name = "name") String name,
                                  @RequestParam (name = "description") String description,
                                  @RequestParam (name = "deadline") String deadline,
                                  @RequestParam (name = "category") Category category,
                                  @RequestParam (name = "finished", required = false) boolean finished) {

            Long savedTaskId;
            LocalDateTime dateTimeDeadline;
            if (!Objects.equals(deadline, "")) {
                dateTimeDeadline = getLocalDateTimeDeadline(deadline);
            } else {
                dateTimeDeadline = null;
            }
            LocalDateTime nowTime = LocalDateTime.now(ZoneId.of("Europe/Berlin"));
            LocalDateTime endTime;

            if (!finished) {
                TaskDto task = new TaskDto(name, description, dateTimeDeadline, category);
                savedTaskId = taskService.saveTask(task, finished);
            } else {
                if (dateTimeDeadline != null && dateTimeDeadline.isBefore(nowTime)){
                    endTime = dateTimeDeadline;
                } else {
                    endTime = nowTime;
                }
                TaskDto finishedTask = new TaskDto(name, description, dateTimeDeadline, category, endTime);
                savedTaskId = taskService.saveTask(finishedTask, finished);
            }
            System.out.println("Zadanie zapisane z identyfikatorem " + savedTaskId);

            return "redirect:/taskAddedOk";
        }

    private static LocalDateTime getLocalDateTimeDeadline(String deadline) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(deadline, formatter);
        return dateTime;
    }

    @GetMapping("/taskAddedOk")
         public String addTaskResult() {
              return "taskAddedOk";
         }

        private List<Task> getAllNotFinishedTasks() {
           return Stream
                   .concat(taskService.getAllNotFinishedTasksBeforeDeadline().stream(),
                           taskService.getAllNotFinishedTasksWithoutDeadline().stream())
                   .collect(Collectors.toList());
        }

        private List<Task> getAllCompletedTasks() {

            return Stream
                    .concat(taskService.getAllCompletedTasks().stream(),
                            taskService.getAllNotFinishedTasksAfterDeadline().stream())
                    .collect(Collectors.toList());
        }
    }
