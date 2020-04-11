package fr.polytech.rlcalm.controllers;

import fr.polytech.rlcalm.beans.Player;
import fr.polytech.rlcalm.exception.InvalidFormException;
import fr.polytech.rlcalm.exception.ServiceException;
import fr.polytech.rlcalm.form.PlayerUpdateForm;
import fr.polytech.rlcalm.initializer.ControllerInitializer;
import fr.polytech.rlcalm.service.ClubService;
import fr.polytech.rlcalm.service.PlayerService;

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
                if (player == null) {
                    //TODO redirect
                } else {
                    req.setAttribute("player", player);
                    forwardToPlayerEdit(req, resp);
                }
            } catch (NumberFormatException e) {
                //TODO
            }
        } else {
            //creation
            forwardToPlayerEdit(req, resp);
        }
    }

    private void forwardToPlayerEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forwardToPlayerEdit(req, resp, null);
    }

    private void forwardToPlayerEdit(HttpServletRequest req, HttpServletResponse resp, String requestParams) throws ServletException, IOException {
        //TODO besoin de l'arrayList ?
        req.setAttribute("clubs", new ArrayList<>(clubService.getAll()));
        getServletContext().getRequestDispatcher("/player_edit.jsp" + (requestParams == null ? "" : requestParams)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParameter = req.getParameter("id");
        String action = req.getParameter("action");
        if (action != null) {
            try {
                switch (action) {
                    case "delete": {
                        long id = Long.parseLong(idParameter);//TODO formUtils ?
                        playerService.delete(playerService.getPlayer(id));
                        resp.sendRedirect("players");
                        break;
                    }
                    case "createOrUpdate": {
                        try {
                            playerService.update(PlayerUpdateForm.fromRequest(req));
                            resp.sendRedirect("players" + (idParameter == null ? "" : "?id=" + idParameter));
                        } catch (InvalidFormException | ServiceException e) {
                            req.setAttribute("error", e.getMessage());
                            forwardToPlayerEdit(req, resp);
                            return;
                        }
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                //TODO error
            }
        }
    }
}
