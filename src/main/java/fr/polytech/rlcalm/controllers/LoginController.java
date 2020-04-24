package fr.polytech.rlcalm.controllers;

import fr.polytech.rlcalm.exception.InvalidFormException;
import fr.polytech.rlcalm.exception.ServiceException;
import fr.polytech.rlcalm.initializer.ControllerInitializer;
import fr.polytech.rlcalm.service.UserService;
import fr.polytech.rlcalm.utils.Constants;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
    name = "Login",
    urlPatterns = "/appLogin"
)
public class LoginController extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        userService = ControllerInitializer.getUserService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter(Constants.ACTION_KEY);
        if (Constants.LOGOUT.equals(action)) {
            req.getSession().removeAttribute("connected");
        } else {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            try {
                req.getSession().setAttribute("connected", userService.authenticate(username, password));
            } catch (ServiceException | InvalidFormException e) {
                req.setAttribute(Constants.LOGIN_ERROR_KEY, e.getMessage());
            }
        }
        resp.sendRedirect("index.jsp");
    }
}
