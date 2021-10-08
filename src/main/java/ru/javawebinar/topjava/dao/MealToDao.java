package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealToDao {

    void createMealTo(MealTo meal);

    MealTo readMealToById(long id);

    List<MealTo> readAllMeals();

    void updateMealTo(MealTo meal);

    void deleteMealTo(long id);


}
