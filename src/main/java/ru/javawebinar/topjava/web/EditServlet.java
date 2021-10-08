package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.MealToDaoImpl;
import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.javawebinar.topjava.web.MealServlet.mealService;

public class EditServlet extends HttpServlet {
    //private static final MealToDaoImpl mealService = new MealToDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        System.out.println("SMTHG!!!");

        String action = req.getParameter("action");
        if (action != null && action.equalsIgnoreCase("edit")) {
            System.out.println("is it?");
            long mealId = Integer.parseInt(req.getParameter("mealId"));
            System.out.println(mealId);
            System.out.println(mealService.getAllMeals().keySet());
            MealTo meal = mealService.readMealToById(mealId);
            System.out.println(meal);
            meal.setDescription(req.getParameter("description"));
            meal.setDateTime(req.getParameter("dateTime").replace("T"," "));
            meal.setCalories(Integer.parseInt(req.getParameter("calories")));
            mealService.updateMealTo(meal);
        }
        else {
            MealTo meal = new MealTo();
            meal.setDateTime(req.getParameter("dateTime").replace("T"," "));
            meal.setCalories(Integer.parseInt(req.getParameter("calories")));
            meal.setDescription(req.getParameter("description"));

            mealService.createMealTo(meal);
        }

        req.setAttribute("mealsList", mealService.readAllMeals());
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);

    }
}
