package fr.polytech.rlcalm.controllers;


import fr.polytech.rlcalm.beans.Club;
import fr.polytech.rlcalm.initializer.ControllerInitializer;
import fr.polytech.rlcalm.service.ClubService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(
        urlPatterns = "/clubs",
        name = "ClubServlet"
)
public class ClubController extends HttpServlet {

    private ClubService clubService;

    @Override
    public void init() {
        clubService = ControllerInitializer.getClubService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parameter = req.getParameter("id");
        if (parameter != null) {
            try {
                Long id = Long.parseLong(parameter);
                Club club = clubService.getClub(id);
                //prevent lazy loading
                req.setAttribute("club", club);
                req.setAttribute("participations", clubService.getParticipationClub(id));
                req.setAttribute("plannified", clubService.getNumberOfPlannifiedMatch(id));
                req.setAttribute("victories", clubService.getNumberOfVictoryClub(id));
                req.setAttribute("palmares", new ArrayList<>(clubService.getPalmaresClub(club)));
                getServletContext().getRequestDispatcher("/club.jsp").forward(req, resp);
                return;
            } catch (NumberFormatException ignored) { }
        }
        req.setAttribute("clubs", clubService.getAll());
        getServletContext().getRequestDispatcher("/clubs.jsp").forward(req, resp);
    }
}
