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
        String action = req.getParameter("action");
        Integer year = null;
        try {
            year = FormUtils.getInt(req.getParameter("year"), null);
            if (action != null) {
                if (year != null) {
                    switch (action) {
                        case "delete" : {
                            return;
                        }
                        case "update" : {
                            req.setAttribute("tournamentResult", tournamentResultService.getByYear(year));
                            req.getRequestDispatcher("/tournamentResult_edit.jsp").forward(req, resp);
                            return;
                        }
                        default:
                    }
                } else {
                    req.setAttribute("error", "Veuillez saisir une année !");
                }
            }
        } catch (Exception e) {
            req.setAttribute("error", "Veuillez saisir une année valide!");
        }

        forwartToTournamentResultSearch(year, req, resp);
    }

    private void forwartToTournamentResultSearch(Integer year, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (year == null) {
            req.setAttribute("results", tournamentResultService.getAll());
        } else {
            req.setAttribute("results", tournamentResultService.getByYear(year));
        }
        getServletContext().getRequestDispatcher("/tournamentResult.jsp").forward(req, resp);
    }
}
