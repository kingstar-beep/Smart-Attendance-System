/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author KINGSTAR
 */
@WebServlet("/CreateSessionServlet")
public class CreateSessionServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String course = request.getParameter("course");
        String latStr = request.getParameter("lat");
        String lngStr = request.getParameter("lng");
        String radiusStr = request.getParameter("radius");

        // VALIDATION (VERY IMPORTANT)
        if (latStr == null || lngStr == null || radiusStr == null
                || latStr.isEmpty() || lngStr.isEmpty() || radiusStr.isEmpty()) {

            response.getWriter().println("Error: Missing location or radius data.");
            return;
        }

        double lat = Double.parseDouble(latStr);
        double lng = Double.parseDouble(lngStr);
        double radius = Double.parseDouble(radiusStr);

        String sessionCode = UUID.randomUUID().toString();
        String qrData = "http://localhost:8080/SmartAttendance/MarkAttendanceServlet?code=" + sessionCode;

        // 🧱 Save session FIRST
        Session session = new Session();
        session.setSessionCode(sessionCode);
        session.setLatitude(lat);
        session.setLongitude(lng);
        session.setRadius(radius);
        
        // Add expiry time
        //session.setExpiryTime(System.currentTimeMillis() + (30 * 60 * 1000)); // 5 minutes

        SessionDAO dao = new SessionDAO();
        dao.saveSession(session);

        // Then forward
        request.setAttribute("sessionCode", sessionCode);
        request.setAttribute("qrData", qrData);

        request.getRequestDispatcher("qr-display.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ❌ Do NOT call doPost blindly
        request.getRequestDispatcher("lecturer.jsp").forward(request, response);
    }
}
