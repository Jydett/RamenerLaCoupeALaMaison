package fr.polytech.rlcalm.controllers;

import fr.polytech.rlcalm.initializer.ControllerInitializer;
import fr.polytech.rlcalm.service.TournamentResultService;
import fr.polytech.rlcalm.utils.Constants;
import fr.polytech.rlcalm.utils.FormUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);//TODO pas tres propre
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter(Constants.ACTION_KEY);
        Integer year = null;
        try {
            if (! FormUtils.isNullOrEmpty(req.getParameter("year"))) {
                year = FormUtils.getInt(req.getParameter("year"), null);
            }
        } catch (Exception e) {
            req.setAttribute(Constants.ERROR_KEY, "Veuillez saisir une année valide!");
        }

        if ("delete".equals(action) || "update".equals(action)) {
            if (year == null) {
                req.setAttribute(Constants.ERROR_KEY, "Veuillez saisir une année !");
            } else {
                Object user = req.getSession().getAttribute("connected");
                if (Objects.isNull(user)) {
                    req.setAttribute(Constants.LOGIN_ERROR_KEY, "Vous devez être connecté pour faire cette action");
                } else {
                    switch (action) {
                        case Constants.DELETE : {
                            tournamentResultService.deleteByYear(year);
                            //after delete, we display all the results
                            year = null;
                            break;
                        }
                        case Constants.UPDATE : {
                            req.setAttribute("tournamentResult", tournamentResultService.getByYear(year));
                            req.getRequestDispatcher("/tournamentResult_edit.jsp").forward(req, resp);
                            return;
                        }
                    }
                }
            }
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
