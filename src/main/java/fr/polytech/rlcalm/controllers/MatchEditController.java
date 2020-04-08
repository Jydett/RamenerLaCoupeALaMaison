package fr.polytech.rlcalm.controllers;

import fr.polytech.rlcalm.beans.Match;
import fr.polytech.rlcalm.exception.ServiceException;
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
        name = "MatchEditController",
        urlPatterns = "/matchEdit"
)
public class MatchEditController extends HttpServlet {//TODO auth filter

    private MatchService matchService;
    private ClubService clubService;

    @Override
    public void init() {
        matchService = ControllerInitializer.getMatchService();
        clubService = ControllerInitializer.getClubService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parameter = req.getParameter("id");
        if (parameter != null) {
            //edition
            try {
                long id = Long.parseLong(parameter);
                Match match = matchService.getMatch(id);
                if (match == null) {
                    //TODO redirect
                } else {
                    req.setAttribute("match", match);
                    forwardToMatchEdit(req, resp);
                }
            } catch (NumberFormatException e) {
                //TODO
            }
        } else {
            //creation
            forwardToMatchEdit(req, resp);
        }
    }

    private void forwardToMatchEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO besoin de l'arrayList ?
        req.setAttribute("clubs", new ArrayList<>(clubService.getAll()));
        getServletContext().getRequestDispatcher("/match_edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParameter = req.getParameter("id");
        String action = req.getParameter("action");
        if (action != null) {
            try {
                switch (action) {
                    case "delete": {
                        long id = Long.parseLong(idParameter);
                        matchService.delete(matchService.getMatch(id));
                        resp.sendRedirect("matches");
                        break;
                    }
                    case "createOrUpdate": {
                        try {
                            matchService.update(MatchUpdateForm.fromRequest(req));
                            resp.sendRedirect("matches");
                        } catch (IllegalArgumentException | ServiceException e) {
                            if (idParameter == null) {
                                resp.sendRedirect("matchEdit?id=" + idParameter);
                            } else {
                                resp.sendRedirect("matchEdit");
                            }
                        }
                        break;
                    }
                }
            } catch (NumberFormatException e) {

            }
        }
    }

}
