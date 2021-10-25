package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryMealRepository implements MealRepository {

    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private InMemoryUserRepository userRepo;
    private final AtomicInteger counter = new AtomicInteger(0);

    public InMemoryMealRepository() {
        this.userRepo = new InMemoryUserRepository();
        MealsUtil.meals.forEach(x -> save(x, SecurityUtil.authUserId()));
    }


    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            int newId = counter.incrementAndGet();
            meal.setId(newId);
            repository.put(meal.getId(), meal);
            userRepo.get(userId).getUserMeal().put(newId,meal);
            return meal;
        }
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        userRepo.get(userId).getUserMeal().remove(id);
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public List<Meal> getAllbyUserId(int userId) {
        return new ArrayList<>(userRepo.get(userId).getUserMeal().values());
    }
}

