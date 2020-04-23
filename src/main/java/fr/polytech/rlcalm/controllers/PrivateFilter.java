package fr.polytech.rlcalm.controllers;

import fr.polytech.rlcalm.utils.Constants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebFilter(
    filterName = "PrivateFilter",
    servletNames = {"MatchEditController", "PlayerEditController", "MatchScoreEditController"}
)
public class PrivateFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        Object user = req.getSession().getAttribute("connected");
        if (Objects.isNull(user)) {
            req.setAttribute(Constants.LOGIN_ERROR_KEY, "Vous devez être connecté pour acceder à cette page");
            req.getRequestDispatcher("index.jsp").forward(req, res);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}

