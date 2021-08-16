package org.example.uniqusers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "UserServlet", value = "/UserServlet")
public class UserServlet extends HttpServlet {
    private final Map<String, String> users = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        String userIp = request.getRemoteHost();
        String userAgent = request.getHeader("User-Agent");
        users.put(userIp, userAgent);
        response.setContentType("text/html");
        try (PrintWriter writer = response.getWriter()) {
            for (String key : users.keySet()) {
                if (key.equals(userIp)) {
                    writer.println("<b>" + key + " :: " + users.get(key) + "</b>");
                } else {
                    writer.println(key + " :: " + users.get(key));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
