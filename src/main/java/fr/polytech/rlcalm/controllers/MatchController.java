package fr.polytech.rlcalm.controllers;

import fr.polytech.rlcalm.initializer.ControllerInitializer;
import fr.polytech.rlcalm.service.MatchService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(
        urlPatterns = "/matches",
        name = "MatchController"
)
public class MatchController extends HttpServlet {

    private MatchService matchService;

    @Override
    public void init() {
        matchService = ControllerInitializer.getMatchService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("matchs", new ArrayList<>(matchService.getAllMatches()));
        getServletContext().getRequestDispatcher("/matchs.jsp").forward(req, resp);
    }
}
