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

        System.out.println(request.getRequestURI());
        request.setAttribute("mealsList",mealService.readAllMeals());
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

}
