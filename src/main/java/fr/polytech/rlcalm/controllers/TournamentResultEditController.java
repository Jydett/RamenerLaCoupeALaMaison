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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] orders = req.getParameterValues("order");
        tournamentResultService.updateOrder(orders);
        resp.sendRedirect("results");
    }
}
