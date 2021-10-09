package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MealTo {
    private LocalDateTime dateTime;

    private String description;

    private int calories;

    private boolean excess = false;

    private static volatile long idSum = 0;
    private volatile long id;

    public MealTo() {
    }

    public MealTo(LocalDateTime dateTime, String description, int calories, boolean excess) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
        synchronized (this) {
            this.id = ++idSum;
        }
    }

    public long getId() {
        return id;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }


    public String getDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedString = dateTime.format(formatter);
        return formattedString;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExcess() {
        return excess;
    }

    public void setDateTime(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime formatDateTime = LocalDateTime.parse(dateTime, formatter);
        this.dateTime = formatDateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setExcess(boolean excess) {
        this.excess = excess;
    }

    @Override
    public String toString() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedString = dateTime.format(formatter);

        return "MealTo{" +
                "dateTime=" + formattedString +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}';
    }
}
