package ru.kpfu.itis.selyantsev.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kpfu.itis.selyantsev.dao.impl.UserDaoImpl;
import ru.kpfu.itis.selyantsev.dto.UserDTO;
import ru.kpfu.itis.selyantsev.models.User;
import static ru.kpfu.itis.selyantsev.util.PasswordUtil.encrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "login", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationServlet.class);

    private final UserDaoImpl userDao = new UserDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("login.ftl");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        UserDTO userDTO = new UserDTO(login, password);

        User user = userDao.findByName(login);

        if (userDTO.getLogin().equals(user.getLogin())) {
            if (encrypt(userDTO.getPassword()).equals(encrypt(user.getPassword()))) {
                resp.sendRedirect("mainPage.ftl");
            }
        }

    }
}
