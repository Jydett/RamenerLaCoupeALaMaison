package fr.polytech.rlcalm.controllers;

import fr.polytech.rlcalm.beans.Match;
import fr.polytech.rlcalm.exception.InvalidFormException;
import fr.polytech.rlcalm.exception.ServiceException;
import fr.polytech.rlcalm.form.MatchUpdateForm;
import fr.polytech.rlcalm.initializer.ControllerInitializer;
import fr.polytech.rlcalm.service.ClubService;
import fr.polytech.rlcalm.service.MatchService;
import fr.polytech.rlcalm.utils.Constants;
import fr.polytech.rlcalm.utils.FormUtils;

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
public class MatchEditController extends HttpServlet {

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
                if (match != null) {
                    req.setAttribute("match", match);
                    forwardToMatchEdit(req, resp);
                    return;
                }
            } catch (NumberFormatException ignored) {
                //redirect
            }
            resp.sendRedirect("matches");
        } else {
            //creation
            forwardToMatchEdit(req, resp);
        }
    }

    private void forwardToMatchEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("clubs", new ArrayList<>(clubService.getAll()));
        getServletContext().getRequestDispatcher("/match_edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter(Constants.ACTION_KEY);
        if (action != null) {
            switch (action) {
                case Constants.DELETE: {
                    try {
                        long id = FormUtils.getRequiredLong(req.getParameter("id"), null);
                        matchService.delete(matchService.getMatch(id));
                        resp.sendRedirect("matches");
                    } catch (InvalidFormException e) {
                        resp.sendRedirect("matches");
                    }
                    return;
                }
                case Constants.CREATE_OR_UPDATE: {
                    try {
                        matchService.update(MatchUpdateForm.fromRequest(req));
                        resp.sendRedirect("matches");
                    } catch (InvalidFormException | ServiceException e) {
                        req.setAttribute(Constants.ERROR_KEY, e.getMessage());
                        forwardToMatchEdit(req, resp);
                    }
                    return;
                }
            }
        }
        resp.sendRedirect("matches");
    }
}