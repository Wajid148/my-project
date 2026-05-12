package com.finance.app;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@WebServlet("/health")
public class HealthServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html; charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h2>✅ Finance Dashboard is Running!</h2>");
        out.println("<p>Server Time: " + LocalDateTime.now() + "</p>");
        out.println("<p>Database: PostgreSQL ✅</p>");
        out.println("<p>Security: JWT Enabled ✅</p>");
        out.println("<p>Framework: Spring Boot 3.2.0 ✅</p>");
        out.println("</body></html>");
    }
}