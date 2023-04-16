package pl.javastart.jjdind49_katibar_zad25_1;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

    @Service
    class TaskService {
        private final TaskRepository taskRepository;
        LocalDateTime localDateTime = LocalDateTime.now();

        public TaskService(TaskRepository taskRepository) {
            this.taskRepository = taskRepository;
        }

        @Transactional
        public Long saveTask(TaskDto task, boolean finished) {
            Task taskToSave;
            if (!finished) {
                taskToSave = new Task(task.getName(), task.getDescription(), task.getDeadline(), task.getCategory());
            } else {
                taskToSave = new Task(task.getName(), task.getDescription(), task.getDeadline(), task.getCategory(), task.getEndTime());
            }
            Task savedTask = taskRepository.save(taskToSave);
            return savedTask.getId();
        }

        List<Task> getAllNotFinishedTasksWithoutDeadline() {
            return taskRepository.findAllByEndTimeIsNull()
                    .stream()
                    .filter(task -> task.getDeadline() == null)
                    .toList();
        }

        List<Task> getAllNotFinishedTasksBeforeDeadline() {
            return taskRepository.findAllByEndTimeIsNull()
                    .stream()
                    .filter(task -> task.getDeadline() != null)
                    .filter(task -> task.getDeadline().isAfter(localDateTime))
                    .sorted(Comparator.comparing(Task::getDeadline))
                    .toList();
        }

        List<Task> getAllCompletedTasks() {
            return taskRepository.findAllByEndTimeIsNotNull()
                    .stream()
                    .toList();
        }

        List<Task> getAllNotFinishedTasksAfterDeadline() {
            return taskRepository.findAllByEndTimeIsNull()
                    .stream()
                    .filter(task -> task.getDeadline() != null)
                    .filter(task -> task.getDeadline().isBefore(localDateTime))
                    .toList();
        }
    }
