package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class MealToDaoImpl implements MealToDao{

    public static void main(String[] args) {
        MealToDaoImpl impl = new MealToDaoImpl();
        System.out.println(impl.allMeals);

    }

    private final Map<Long,MealTo> allMeals = new ConcurrentHashMap<>();
    private static final int CALORIES = 2000;

    public MealToDaoImpl() {
        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Ананасы", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Пиво", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Крабы", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Раки", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Креветки", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Паста", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Мороженное", 410)
        );

        List<MealTo> result = MealsUtil.mealConvertToMealTo(meals,CALORIES);
        result.forEach(x -> allMeals.put(x.getId(),x));
    }

    public Map<Long, MealTo> getAllMeals() {
        return allMeals;
    }

    @Override
    public void createMealTo(MealTo meal) {
        allMeals.put(meal.getId(),meal);
        defineExcess(meal.getDate());
    }

    @Override
    public MealTo readMealToById(long id) {
        return allMeals.get(id);
    }

    @Override
    public List<MealTo> readAllMeals() {
        List<MealTo> list = new ArrayList<>(allMeals.values());
        return list;
    }

    @Override
    public void updateMealTo(MealTo meal) {
        defineExcess(meal.getDate());
        allMeals.put(meal.getId(), meal);
    }

    @Override
    public void deleteMealTo(long id) {
        LocalDate date = allMeals.get(id).getDate();
        allMeals.remove(id);
        defineExcess(date);
    }

    private void defineExcess(LocalDate date) {
        final int[] sum = {0};
        List<MealTo> result = allMeals.values().stream().filter(x -> x.getDate().equals(date)).collect(Collectors.toList());
        result.forEach(x -> sum[0] += x.getCalories());
        result.forEach(x -> x.setExcess(sum[0] > CALORIES));
    }
}
