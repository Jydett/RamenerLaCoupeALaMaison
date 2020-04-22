package fr.polytech.rlcalm.controllers;

import fr.polytech.rlcalm.beans.Player;
import fr.polytech.rlcalm.exception.InvalidFormException;
import fr.polytech.rlcalm.exception.ServiceException;
import fr.polytech.rlcalm.form.PlayerUpdateForm;
import fr.polytech.rlcalm.initializer.ControllerInitializer;
import fr.polytech.rlcalm.service.ClubService;
import fr.polytech.rlcalm.service.PlayerService;
import fr.polytech.rlcalm.utils.FormUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(
        urlPatterns = "/playerEdit",
        name = "PlayerEditController"
)
public class PlayerEditController extends HttpServlet {

    private PlayerService playerService;
    private ClubService clubService;

    @Override
    public void init() {
        playerService = ControllerInitializer.getPlayerService();
        clubService = ControllerInitializer.getClubService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parameter = req.getParameter("id");
        if (parameter != null) {
            //edition
            try {
                long id = Long.parseLong(parameter);
                Player player = playerService.getPlayer(id);
                if (player != null) {
                    req.setAttribute("player", player);
                    forwardToPlayerEdit(req, resp);
                    return;
                }
            } catch (NumberFormatException e) {
                //redirect
            }
            resp.sendRedirect("/players");
        } else {
            //creation
            forwardToPlayerEdit(req, resp);
        }
    }

    private void forwardToPlayerEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("clubs", new ArrayList<>(clubService.getAll()));
        getServletContext().getRequestDispatcher("/player_edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParameter = req.getParameter("id");
        String action = req.getParameter("action");
        if (action != null) {
            switch (action) {
                case "delete": {
                    try {
                        long id = FormUtils.getRequiredLong(idParameter, null);
                        playerService.delete(playerService.getPlayer(id));
                        resp.sendRedirect("players");
                    } catch (InvalidFormException e) {
                        resp.sendRedirect("players");
                    }
                    return;
                }
                case "createOrUpdate": {
                    try {
                        playerService.update(PlayerUpdateForm.fromRequest(req));
                        resp.sendRedirect("players" + (idParameter == null ? "" : "?id=" + idParameter));
                    } catch (InvalidFormException | ServiceException e) {
                        req.setAttribute("error", e.getMessage());
                        forwardToPlayerEdit(req, resp);
                    }
                    return;
                }
            }
        }
        resp.sendRedirect("/players");
    }
}
