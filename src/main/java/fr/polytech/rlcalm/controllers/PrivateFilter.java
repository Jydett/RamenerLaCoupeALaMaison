package fr.polytech.rlcalm.controllers;

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
            res.sendRedirect("/index.jsp");
        } else {
            filterChain.doFilter(request, response);
        }
    }
}

