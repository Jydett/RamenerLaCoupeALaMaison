package fr.polytech.rlcalm.controllers;

import fr.polytech.rlcalm.beans.Player;
import fr.polytech.rlcalm.initializer.ControllerInitializer;
import fr.polytech.rlcalm.service.PlayerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        urlPatterns = "/players",
        name = "PlayerServlet"
)
public class PlayerController extends HttpServlet {

    private PlayerService playerService;

    @Override
    public void init() {
        playerService = ControllerInitializer.getPlayerService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parameter = req.getParameter("id");
        if (parameter != null) {
            try {
                Long id = Long.parseLong(parameter);
                Player player = playerService.getPlayer(id);
                req.setAttribute("participations", playerService.getParticipationOfPlayer(player));
                req.setAttribute("player", player);
                getServletContext().getRequestDispatcher("/player.jsp").forward(req, resp);
            } catch (NumberFormatException ignored) { }
        }
        req.setAttribute("players", playerService.getAll());
        getServletContext().getRequestDispatcher("/players.jsp").forward(req, resp);
    }
}
