package fr.polytech.rlcalm.controllers;

import fr.polytech.rlcalm.beans.Match;
import fr.polytech.rlcalm.exception.InvalidFormException;
import fr.polytech.rlcalm.exception.ServiceException;
import fr.polytech.rlcalm.form.ParticipationUpdateForm;
import fr.polytech.rlcalm.initializer.ControllerInitializer;
import fr.polytech.rlcalm.service.MatchService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "MatchScoreEditController",
        urlPatterns = "/matchScoreEdit"
)
public class MatchScoreEditController extends HttpServlet {//TODO auth filter

    private MatchService matchService;

    @Override
    public void init() {
        matchService = ControllerInitializer.getMatchService();
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
                    req.setAttribute("participations1", matchService.getParticipationsOfPlayer1OnMatch(match));
                    req.setAttribute("participations2", matchService.getParticipationsOfPlayer2OnMatch(match));
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
        forwardToMatchEdit(req, resp, null);
    }

    private void forwardToMatchEdit(HttpServletRequest req, HttpServletResponse resp, String requestParams) throws ServletException, IOException {
        //TODO besoin de l'arrayList ?
        getServletContext().getRequestDispatcher("/match_score_edit.jsp" + (requestParams == null ? "" : requestParams)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParameter = req.getParameter("matchId");
        String action = req.getParameter("action");
        if (action != null) {
            try {
                long id = Long.parseLong(idParameter);//TODO formUtils ?
                Match match = matchService.getMatch(id);//TODO error
                switch (action) {
                    case "delete": {
                        matchService.removeScoring(match);
                        resp.sendRedirect("matches");
                        break;
                    }
                    case "update": {
                        try {
                            matchService.updateParticipation(match, ParticipationUpdateForm.formRequest(req));
                            resp.sendRedirect("matches");
                        } catch (InvalidFormException | ServiceException e) {
                            req.setAttribute("error", e.getMessage());
                            forwardToMatchEdit(req, resp);
                        }
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                //TODO error
            }
        }
        //TODO redirect ?
    }

}
