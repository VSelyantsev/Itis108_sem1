package ru.kpfu.itis.selyantsev.servlet;

import com.sun.corba.se.spi.transport.CorbaConnection;
import ru.kpfu.itis.selyantsev.net.Net;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Connection;

@WebServlet(name = "main", urlPatterns = "/main")
public class MainPage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("mainPage.ftl");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String u = "";
        String city = req.getParameter("city");
        String api = "6faded995f07a67bd6431a5176bb4640";
        Net net = new Net();
        PrintWriter pw = null;

        try {
            URL url = new URL(u + "?" + "q=" + city + "&" + "appid" + api);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setDoOutput(true);
            connection.setDoInput(true);

            pw = new PrintWriter(connection.getOutputStream());
            pw.print("q=" + city + "&" + "appid" + api);
            pw.flush();


        } catch (IOException e) {
            throw new RemoteException(e);
        }

    }
}
