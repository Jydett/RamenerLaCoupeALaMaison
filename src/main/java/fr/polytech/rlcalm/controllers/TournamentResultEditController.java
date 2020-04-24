package fr.polytech.rlcalm.controllers;

import fr.polytech.rlcalm.exception.InvalidFormException;
import fr.polytech.rlcalm.exception.ServiceException;
import fr.polytech.rlcalm.initializer.ControllerInitializer;
import fr.polytech.rlcalm.service.TournamentResultService;
import fr.polytech.rlcalm.utils.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
    urlPatterns = "/resultsEdit",
    name = "TournamentResultEditController"
)
public class TournamentResultEditController extends HttpServlet {

    private TournamentResultService tournamentResultService;

    @Override
    public void init() {
        tournamentResultService = ControllerInitializer.getTournamentResultService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String[] orders = req.getParameterValues("order");
        try {
            if (orders == null || orders.length == 0) {
                throw new InvalidFormException("Il faut au moins une équipe pour faire un classement");
            } else if (Constants.CREATE.equals(req.getParameter("action"))) {
                String year = req.getParameter("year");
                tournamentResultService.saveCreation(year, orders);
            } else {
                tournamentResultService.updateOrder(orders);
            }
            resp.sendRedirect("results");
            return;
        } catch (InvalidFormException | ServiceException e) {
            req.setAttribute(Constants.ERROR_KEY, e.getMessage());
        }
        req.getRequestDispatcher("/results").forward(req, resp);
    }
}
