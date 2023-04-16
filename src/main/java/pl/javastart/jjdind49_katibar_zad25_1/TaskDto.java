package pl.javastart.jjdind49_katibar_zad25_1;

import java.time.LocalDateTime;

public class TaskDto {
    private final String name;
    private final String description;
    private final LocalDateTime deadline;
    private final Category category;
    private LocalDateTime endTime;

    public TaskDto(String name, String description, LocalDateTime deadline, Category category, LocalDateTime endTime) {
        this(name, description, deadline, category);
        this.endTime = endTime;
    }

    public TaskDto(String name, String description, LocalDateTime deadline, Category category) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public Category getCategory() {
        return category;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
