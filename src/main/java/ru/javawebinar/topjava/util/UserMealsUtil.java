package ru.javawebinar.topjava.util;

import org.w3c.dom.ls.LSOutput;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

//        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        List<UserMealWithExcess> result = new ArrayList<>();
        LinkedList<UserMeal> list = new LinkedList<>(meals);
        Map<LocalDate,Integer> map = new HashMap<>();

        for (int i = 0; i < meals.size()*2; i++) {

            UserMeal meal = list.poll();

            if(i >= meals.size() && !list.contains(meal)){

                if(meal == null)
                    break;

                boolean excess = map.get(meal.getDateTime().toLocalDate()) > caloriesPerDay ? true : false;
                result.add(new UserMealWithExcess(meal.getDateTime(),meal.getDescription(),meal.getCalories(),excess));
            }

            else {
                LocalDate date = meal.getDateTime().toLocalDate();
                int dayValue = meal.getCalories();
                map.merge(date,dayValue,(oldVal,newVal) -> oldVal + newVal);
                if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                    list.offer(meal);
                }
            }
        }
        return result;
    }

    public static List<UserMealWithExcess> filteredByCycles2 (List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        List<UserMealWithExcess> result = new ArrayList<>();
        Map<LocalDate,Integer> map = new HashMap<>();

        for(UserMeal meal : meals) {
            LocalDate date = meal.getDateTime().toLocalDate();
            int dayValue = meal.getCalories();
            map.merge(date,dayValue,(oldVal,newVal) -> oldVal + newVal);
        }
        System.out.println(map);
        for(UserMeal meal : meals) {
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                boolean excess = map.get(meal.getDateTime().toLocalDate()) > caloriesPerDay ? true : false;
                result.add(new UserMealWithExcess(meal.getDateTime(),meal.getDescription(),meal.getCalories(),excess));
            }
        }
        return result;
    }

    
    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        //Map<LocalDate,Integer> map = meals.stream().collect(Collectors.toMap(x -> x.getDateTime().toLocalDate(), x -> x.getCalories(),(x,y) -> x+y));
        Map<LocalDate,Integer> map = meals.stream().collect(Collectors.toMap(UserMeal::getLocalDate, UserMeal::getCalories,Integer::sum));

        return meals.stream().filter(x -> TimeUtil.isBetweenHalfOpen(x.getDateTime().toLocalTime(),startTime,endTime))
                .map(meal -> new UserMealWithExcess(meal.getDateTime(),meal.getDescription(),meal.getCalories(),map.get(meal.getLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
