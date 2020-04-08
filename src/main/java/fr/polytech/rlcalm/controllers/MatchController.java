package fr.polytech.rlcalm.controllers;

import fr.polytech.rlcalm.beans.Match;
import fr.polytech.rlcalm.form.MatchUpdateForm;
import fr.polytech.rlcalm.initializer.ControllerInitializer;
import fr.polytech.rlcalm.service.ClubService;
import fr.polytech.rlcalm.service.MatchService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(
        name = "MatchController",
        urlPatterns = "/matchEdit"
)
public class MatchController extends HttpServlet {//TODO auth filter

    private MatchService matchService;
    private ClubService clubService;

    @Override
    public void init() {
        matchService = ControllerInitializer.getMatchService();
        clubService = ControllerInitializer.getClubService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String parameter = req.getParameter("id");
        if (parameter != null) {
            try {
                long id = Long.parseLong(parameter);
                Match match = matchService.getMatch(id);
                if (match == null) {
                    //TODO redirect
                } else {
                    req.setAttribute("match", match);
                    //TODO besoin de l'arrayList ?
                    req.setAttribute("clubs", new ArrayList<>(clubService.getAll()));
                    getServletContext().getRequestDispatcher("/match_edit.jsp").forward(req, resp);
                }
            } catch (Exception e) {

            }
        }
    }

    //TODO post save / delete


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String idParameter = req.getParameter("id");
        String action = req.getParameter("action");
        if (idParameter != null) {
            try {
                long id = Long.parseLong(idParameter);
                Match match = matchService.getMatch(id);
                if (match == null) {
                    //TODO redirect with error !
                    return;
                }
                switch (action) {
                    case "delete": {
                        matchService.delete(match);
                        break;
                    }
                    case "update": {
                        matchService.update(match, MatchUpdateForm.fromRequest(req));
                        break;
                    }
                }
            } catch (NumberFormatException e) {

            }
        }
    }

}
