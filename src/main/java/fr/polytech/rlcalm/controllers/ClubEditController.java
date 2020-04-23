package fr.polytech.rlcalm.controllers;

import fr.polytech.rlcalm.beans.Club;
import fr.polytech.rlcalm.exception.InvalidFormException;
import fr.polytech.rlcalm.exception.ServiceException;
import fr.polytech.rlcalm.form.ClubUpdateForm;
import fr.polytech.rlcalm.initializer.ControllerInitializer;
import fr.polytech.rlcalm.service.ClubService;
import fr.polytech.rlcalm.service.CountryService;
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
    name = "ClubEditController",
    urlPatterns = "/clubEdit"
)
public class ClubEditController extends HttpServlet {

    private CountryService countryService;
    private ClubService clubService;

    @Override
    public void init() {
        countryService = ControllerInitializer.getCountryService();
        clubService = ControllerInitializer.getClubService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parameter = req.getParameter("id");
        if (parameter != null) {
            //edition
            try {
                long id = Long.parseLong(parameter);
                Club club = clubService.getClub(id);
                if (club != null) {
                    req.setAttribute("club", club);
                    forwardToClubEdit(req, resp);
                    return;
                }
            } catch (NumberFormatException ignored) {
                //redirect
            }
            resp.sendRedirect("/clubs");
        } else {
            //creation
            forwardToClubEdit(req, resp);
        }
    }

    private void forwardToClubEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("countries", new ArrayList<>(countryService.getAll()));
        getServletContext().getRequestDispatcher("/club_edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParameter = req.getParameter("id");
        String action = req.getParameter(Constants.ACTION_KEY);
        if (action != null) {
            switch (action) {
                case Constants.DELETE: {
                    try {
                        long id = FormUtils.getRequiredLong(idParameter, null);
                        clubService.delete(clubService.getClub(id));
                        resp.sendRedirect("clubs");
                    } catch (InvalidFormException e) {
                        resp.sendRedirect("clubs");
                    }
                    return;
                }
                case Constants.CREATE_OR_UPDATE: {
                    try {
                        clubService.update(ClubUpdateForm.fromRequest(req));
                        resp.sendRedirect("clubs" + (idParameter == null ? "" : "?id=" + idParameter));
                    } catch (InvalidFormException | ServiceException e) {
                        req.setAttribute(Constants.ERROR_KEY, e.getMessage());
                        forwardToClubEdit(req, resp);
                    }
                    return;
                }
            }
        }
        resp.sendRedirect("/clubs");
    }

}
