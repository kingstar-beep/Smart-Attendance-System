/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

/**
 *
 * @author KINGSTAR
 */

/*
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
 */
import model.Session;
import java.io.IOException;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Lecturer;

@WebServlet("/CreateSessionServlet")
public class CreateSessionServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Lecturer must be logged in
        HttpSession httpSession = request.getSession(false);

        if (httpSession == null
                || httpSession.getAttribute("lecturer") == null) {

            response.sendRedirect("lecturerLogin.jsp");
            return;
        }

        Lecturer lecturer
                = (Lecturer) httpSession.getAttribute("lecturer");

        String lecturerId = lecturer.getLecturerId();

        String course = request.getParameter("course");
        String latStr = request.getParameter("lat");
        String lngStr = request.getParameter("lng");
        String radiusStr = request.getParameter("radius");
        String durationStr = request.getParameter("duration");

        // Validate course
        if (course == null || course.trim().isEmpty()) {

            request.setAttribute(
                    "error",
                    "Please enter the course name."
            );

            response.sendRedirect(
                    "LecturerDashboardServlet"
            );

            return;
        }

        // Validate location and radius
        if (latStr == null || lngStr == null
                || radiusStr == null
                || latStr.trim().isEmpty()
                || lngStr.trim().isEmpty()
                || radiusStr.trim().isEmpty()) {

            request.setAttribute(
                    "error",
                    "Location or attendance radius is missing."
            );

            response.sendRedirect(
                    "LecturerDashboardServlet"
            );

            return;
        }

        try {

            double lat = Double.parseDouble(latStr);
            double lng = Double.parseDouble(lngStr);
            double radius = Double.parseDouble(radiusStr);
            int duration = Integer.parseInt(durationStr);

            if (radius <= 0) {

                request.setAttribute(
                        "error",
                        "Attendance radius must be greater than zero."
                );

                response.sendRedirect(
                        "LecturerDashboardServlet"
                );

                return;
            }
            if (duration < 0) {

                request.setAttribute(
                        "error",
                        "Invalid attendance session duration."
                );

                response.sendRedirect(
                        "LecturerDashboardServlet"
                );

                return;
            }

            // Generate unique attendance session
            String sessionCode
                    = UUID.randomUUID().toString();

            // Create session object
            Session attendanceSession
                    = new Session();

            attendanceSession.setSessionCode(
                    sessionCode);

            attendanceSession.setLecturerId(
                    lecturerId);

            attendanceSession.setCourse(
                    course.trim());

            attendanceSession.setLatitude(lat);

            attendanceSession.setLongitude(lng);

            attendanceSession.setRadius(radius);

            attendanceSession.setStatus("ACTIVE");

            /*
             * Expiry can be enabled later.
             * For now the session remains active.
             */
 /* attendanceSession.setExpiryTime(0); */
 /*
 * Set session expiry time.
 *
 * Duration of 0 means that the lecturer
 * has chosen manual session control.
             */
            long expiryTime = 0;

            if (duration > 0) {

                expiryTime
                        = System.currentTimeMillis()
                        + (duration * 60L * 1000L);
            }

            attendanceSession.setExpiryTime(
                    expiryTime
            );

            SessionDAO dao
                    = new SessionDAO();

            boolean saved
                    = dao.saveSession(attendanceSession);

            if (!saved) {

                request.setAttribute(
                        "error",
                        "Unable to create attendance session."
                );

                response.sendRedirect(
                        "LecturerDashboardServlet"
                );

                return;
            }

            /*
             * Build QR attendance URL using
             * the current application context.
             */
            String qrData
                    = request.getScheme()
                    + "://"
                    + request.getServerName()
                    + ":"
                    + request.getServerPort()
                    + request.getContextPath()
                    + "/MarkAttendanceServlet?code="
                    + sessionCode;

            request.setAttribute(
                    "sessionCode",
                    sessionCode
            );

            request.setAttribute(
                    "qrData",
                    qrData
            );

            request.setAttribute(
                    "course",
                    course.trim()
            );

            request.setAttribute(
                    "expiryTime",
                    expiryTime
            );

            request.setAttribute(
                    "duration",
                    duration
            );

            request.getRequestDispatcher(
                    "qr-display.jsp"
            ).forward(request, response);

        } catch (NumberFormatException e) {

            request.setAttribute(
                    "error",
                    "Invalid location or radius value."
            );

            response.sendRedirect(
                    "LecturerDashboardServlet"
            );
        }
    }

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session
                = request.getSession(false);

        if (session == null
                || session.getAttribute("lecturer") == null) {

            response.sendRedirect(
                    "lecturerLogin.jsp"
            );

            return;
        }

        // Session creation now belongs to dashboard
        response.sendRedirect(
                "LecturerDashboardServlet"
        );
    }
}
