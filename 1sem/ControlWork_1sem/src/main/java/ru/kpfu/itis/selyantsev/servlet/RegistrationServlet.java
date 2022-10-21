package ru.kpfu.itis.selyantsev.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kpfu.itis.selyantsev.dao.impl.UserDaoImpl;
import ru.kpfu.itis.selyantsev.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "registration", urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationServlet.class);
    private final UserDaoImpl userDao = new UserDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("registration.ftl");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String firstName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        userDao.save(new User(
                firstName,
                lastName,
                login,
                password,
                email
        ));

        resp.sendRedirect("*.ftl");
    }

}
