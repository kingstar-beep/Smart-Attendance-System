
import model.Lecturer;
import model.Session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CloseSessionServlet")
public class CloseSessionServlet extends HttpServlet {

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession httpSession
                = request.getSession(false);

        // Lecturer must be logged in
        if (httpSession == null
                || httpSession.getAttribute("lecturer") == null) {

            response.sendRedirect(
                    "lecturerLogin.jsp"
            );

            return;
        }

        Lecturer lecturer
                = (Lecturer) httpSession.getAttribute(
                        "lecturer"
                );

        String sessionCode
                = request.getParameter("sessionCode");

        if (sessionCode == null
                || sessionCode.trim().isEmpty()) {

            response.sendRedirect(
                    "LecturerDashboardServlet"
            );

            return;
        }

        SessionDAO sessionDAO
                = new SessionDAO();

        Session attendanceSession
                = sessionDAO.getSessionByCode(
                        sessionCode
                );

        // Session must exist
        if (attendanceSession == null) {

            response.sendRedirect(
                    "LecturerDashboardServlet"
            );

            return;
        }

        // Lecturer can only close their own session
        if (!lecturer.getLecturerId().equals(
                attendanceSession.getLecturerId())) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN
            );

            return;
        }

        // Only an active session can be closed
        if (!"ACTIVE".equalsIgnoreCase(
                attendanceSession.getStatus())) {

            response.sendRedirect(
                    "LecturerDashboardServlet"
            );

            return;
        }

        boolean closed
                = sessionDAO.closeSession(
                        sessionCode,
                        lecturer.getLecturerId()
                );

        if (closed) {

            request.setAttribute(
                    "message",
                    "Attendance session closed successfully."
            );
        }

        response.sendRedirect(
                "LecturerDashboardServlet"
        );
    }
}
