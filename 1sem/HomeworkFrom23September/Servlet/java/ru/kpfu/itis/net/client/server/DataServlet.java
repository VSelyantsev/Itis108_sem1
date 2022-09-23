package ru.kpfu.itis.net.client.server;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;


@WebServlet(name = "DataServlet", urlPatterns = "/date")
public class DataServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("date.ftl");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("firstname");
        String date = req.getParameter("date");

        HttpSession session = req.getSession();
        session.setAttribute("firstname", name);
        session.setAttribute("date", date);

        session.setMaxInactiveInterval(60 * 60);

        Cookie cookieName = new Cookie("firstname", name);
        Cookie cookieDate = new Cookie("date", date);
        resp.addCookie(cookieName);
        resp.addCookie(cookieDate);

        req.getRequestDispatcher("PageWithInfo.ftl").forward(req, resp);


    }

    public static String dateAsString(String date) {

        // format (dd.MM.yyyy) 15.07.2003
        int years = Integer.parseInt(date.substring(6));

        GregorianCalendar gregorianCalendar = new GregorianCalendar();

        int localDateYear = Integer.parseInt(
                String.valueOf(gregorianCalendar.get(Calendar.MONTH) + 1)
        );

        int ageInDays = (localDateYear - years) * 365;
        int ageInMonths = ((localDateYear - years) * 365) / 30;
        int ageInYears = localDateYear - years;

        return "DAYS " + ageInDays + " MONTHS " + ageInMonths + " YEARS " + ageInYears;
    }


}
