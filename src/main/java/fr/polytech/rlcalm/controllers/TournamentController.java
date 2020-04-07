package fr.polytech.rlcalm.controllers;

import fr.polytech.rlcalm.beans.Club;
import fr.polytech.rlcalm.beans.Country;
import fr.polytech.rlcalm.beans.Match;
import fr.polytech.rlcalm.beans.MatchResult;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

@WebServlet(
        urlPatterns = "/home",
        name = "TournamentController"
)
public class TournamentController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Club FR = new Club(0L, "Equipe de France", new Country("France", "fr"));
        Club ALL = new Club(0L, "Les lions de Berlins", new Country("Allemagne", "de"));
        Match match1 = new Match(0L, "Paris", "Stade Lavillette", Instant.now(), null, FR, ALL, null);
        Match match2 = new Match(0L, "Paris", "Stade Lavillette", Instant.now().minus(1, ChronoUnit.DAYS), null, FR, ALL, new MatchResult(3, 0, 1));

        req.setAttribute("matchs", Arrays.asList(match1, match2));
        getServletContext().getRequestDispatcher("/matchs.jsp").forward(req, resp);
    }
}
