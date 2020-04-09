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
                req.setAttribute("club", club);
                getServletContext().getRequestDispatcher("/club.jsp").forward(req, resp);
            } catch (NumberFormatException ignored) { }
        }
        req.setAttribute("clubs", clubService.getAll());
        getServletContext().getRequestDispatcher("/clubs.jsp").forward(req, resp);
    }
}
