package fr.polytech.rlcalm.controllers;

import fr.polytech.rlcalm.beans.Match;
import fr.polytech.rlcalm.exception.InvalidFormException;
import fr.polytech.rlcalm.exception.ServiceException;
import fr.polytech.rlcalm.form.ParticipationUpdateForm;
import fr.polytech.rlcalm.initializer.ControllerInitializer;
import fr.polytech.rlcalm.service.MatchService;
import fr.polytech.rlcalm.utils.Constants;
import fr.polytech.rlcalm.utils.FormUtils;

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
public class MatchScoreEditController extends HttpServlet {

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
                if (match != null) {
                    req.setAttribute("match", match);
                    req.setAttribute("participations1", matchService.getParticipationsOfPlayer1OnMatch(match));
                    req.setAttribute("participations2", matchService.getParticipationsOfPlayer2OnMatch(match));
                    forwardToMatchEdit(req, resp);
                    return;
                }
            } catch (NumberFormatException e) {
                //redirect
            }
            resp.sendRedirect("matches");
        } else {
            //creation
            forwardToMatchEdit(req, resp);
        }
    }

    private void forwardToMatchEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/match_score_edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter(Constants.ACTION_KEY);
        if (action != null) {
            try {
                long id = FormUtils.getRequiredLong(req.getParameter("matchId"), null);
                Match match = matchService.getMatch(id);
                if (match != null) {
                    switch (action) {
                        case Constants.DELETE: {
                            matchService.removeScoring(match);
                            resp.sendRedirect("matches");
                            return;
                        }
                        case Constants.UPDATE: {
                            try {
                                matchService.updateParticipation(match, ParticipationUpdateForm.formRequest(req));
                                resp.sendRedirect("matches");
                            } catch (InvalidFormException | ServiceException e) {
                                req.setAttribute(Constants.ERROR_KEY, e.getMessage());
                                forwardToMatchEdit(req, resp);
                            }
                            return;
                        }
                    }
                }
            } catch (NumberFormatException e) {
                //redirect
            }
        }
        resp.sendRedirect("matches");
    }
}
