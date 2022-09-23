<%@ page import="java.util.Arrays" %><%--
  Created by IntelliJ IDEA.
  User: Vladislav
  Date: 23.09.2022
  Time: 20:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main page</title>
</head>
<body>

    <%
        String user = null;
        String sessionUser = (String) session.getAttribute("firstname");

        if (sessionUser == null) {
            response.sendRedirect("date.ftl");
        } else {
            user = sessionUser;
        }


        String cookieUser = null;
        String cookieDate = null;
        String sessionId = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("firstname".equals(cookie.getName())) cookieUser = cookie.getValue();
                if ("date".equals(cookie.getName())) cookieDate = cookie.getValue();
                if ("JSESSIONID".equals(cookie.getName())) sessionId = cookie.getValue();
            }
        } else {
            sessionId = session.getId();
        }


    %>

</body>
</html>
