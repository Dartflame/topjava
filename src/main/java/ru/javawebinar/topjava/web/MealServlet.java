package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealToDaoImpl;
import ru.javawebinar.topjava.model.MealTo;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    static final MealToDaoImpl mealService = new MealToDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        log.debug("redirect to meals");
        String action = request.getParameter("action");

        if (action != null && action.equalsIgnoreCase("delete")) {
            long mealId = Integer.parseInt(request.getParameter("mealId"));
            mealService.deleteMealTo(mealId);
        }
        if (action != null && (action.equalsIgnoreCase("edit") || action.equalsIgnoreCase("create"))) {
            request.getRequestDispatcher("/edit.jsp").forward(request, response);
        }

        else{
            request.setAttribute("mealsList",mealService.readAllMeals());
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if (action != null && action.equalsIgnoreCase("edit")) {

            long mealId = Integer.parseInt(req.getParameter("mealId"));

            MealTo meal = mealService.readMealToById(mealId);
            meal.setDescription(req.getParameter("description"));
            meal.setDateTime(req.getParameter("dateTime").replace("T"," "));
            meal.setCalories(Integer.parseInt(req.getParameter("calories")));

            mealService.updateMealTo(meal);
        }
        else if(action != null && action.equalsIgnoreCase("create")){

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
