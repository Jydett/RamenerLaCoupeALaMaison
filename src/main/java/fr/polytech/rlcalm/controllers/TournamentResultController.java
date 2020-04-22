package fr.polytech.rlcalm.controllers;

import fr.polytech.rlcalm.initializer.ControllerInitializer;
import fr.polytech.rlcalm.service.TournamentResultService;
import fr.polytech.rlcalm.utils.FormUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        urlPatterns = "/results",
        name = "TournamentResultController"
)
public class TournamentResultController extends HttpServlet {

    private TournamentResultService tournamentResultService;

    @Override
    public void init() {
        tournamentResultService = ControllerInitializer.getTournamentResultService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Integer year = FormUtils.getInt(req.getParameter("year"), null);
            if (year == null) {
                req.setAttribute("results", tournamentResultService.getAll());
            } else {
                req.setAttribute("results", tournamentResultService.getByYear(year));
            }
        } catch (Exception e) {
            req.setAttribute("results", tournamentResultService.getAll());
        }
        getServletContext().getRequestDispatcher("/tournamentResult.jsp").forward(req, resp);
    }
}
