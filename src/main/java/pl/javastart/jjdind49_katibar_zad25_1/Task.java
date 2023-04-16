package pl.javastart.jjdind49_katibar_zad25_1;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm")
    private LocalDateTime endTime;
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm")
    private LocalDateTime deadline;

    @Enumerated(EnumType.STRING)
    private Category category;

    public Task() {
    }

    public Task(String name, String description, LocalDateTime deadline, Category category, LocalDateTime endTime) {
        this(name, description, deadline, category);
        this.endTime = endTime;
    }

    public Task(String name, String description, LocalDateTime deadline, Category category) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        boolean orAfterDeadline = false;
        String info;
        String end = endTime != null ? "Zadanie zakończono." : "Nie zakończono zadania";

        if (deadline != null) {
            if (endTime == null && deadline.isBefore(LocalDateTime.now())) {
                orAfterDeadline = true;
            }
        }

        if (deadline == null) {
            info = "Zadanie bez ograniczonego czasu wykonania\n\n";
        } else {
            info = orAfterDeadline ? String.format("%s\n Termin zadania upłynął %s. \n\n",
                    end, deadline) : String.format("%s. \n" +
                    "Termin zadania upłynie: %s\n\n", end, deadline);
        }

        return info;
    }
}
