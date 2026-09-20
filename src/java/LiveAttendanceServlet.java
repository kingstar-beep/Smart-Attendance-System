import model.Session;
import model.Attendance;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Lecturer;

@WebServlet("/LiveAttendanceServlet")
public class LiveAttendanceServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        if (session == null
                || session.getAttribute("lecturer") == null) {

            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        String sessionCode =
                request.getParameter("sessionCode");

        if (sessionCode == null
                || sessionCode.trim().isEmpty()) {

            response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST);

            return;
        }

        Lecturer lecturer =
                (Lecturer) session.getAttribute("lecturer");

        /*
         * Verify that this session belongs
         * to the logged-in lecturer.
         */
        SessionDAO sessionDAO =
                new SessionDAO();

        Session attendanceSession =
                sessionDAO.getSessionByCode(
                        sessionCode);

        if (attendanceSession == null
                || !lecturer.getLecturerId()
                        .equals(attendanceSession.getLecturerId())) {

            response.setStatus(
                    HttpServletResponse.SC_FORBIDDEN);

            return;
        }

        AttendanceDAO dao =
                new AttendanceDAO();

        List<Attendance> records =
                dao.getAttendanceBySession(
                        sessionCode);

        response.setContentType(
                "application/json");

        response.setCharacterEncoding("UTF-8");

        PrintWriter out =
                response.getWriter();

        out.print("[");

        for (int i = 0; i < records.size(); i++) {

            Attendance a = records.get(i);

            if (i > 0) {
                out.print(",");
            }

            out.print("{");

            out.print("\"studentId\":\""
                    + escape(a.getStudentId())
                    + "\",");

            out.print("\"studentName\":\""
                    + escape(a.getStudentName())
                    + "\",");

            out.print("\"checkInTime\":\""
                    + escape(a.getCheckInTime())
                    + "\",");

            out.print("\"imagePath\":\""
                    + escape(a.getImagePath())
                    + "\"");

            out.print("}");
        }

        out.print("]");

        out.flush();
    }

    private String escape(String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "")
                .replace("\n", "\\n");
    }
}