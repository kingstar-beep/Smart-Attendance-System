import model.Attendance;
import model.Lecturer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AttendanceAnalyticsServlet")
public class AttendanceAnalyticsServlet extends HttpServlet {

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        // Lecturer must be authenticated
        if (session == null
                || session.getAttribute("lecturer") == null) {

            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED
            );

            return;
        }

        Lecturer lecturer =
                (Lecturer) session.getAttribute("lecturer");

        String lecturerId =
                lecturer.getLecturerId();

        AttendanceDAO attendanceDAO =
                new AttendanceDAO();

        List<Attendance> attendanceList =
                attendanceDAO.getAttendanceByLecturer(
                        lecturerId
                );

        /*
         * Group attendance records by session code.
         *
         * Each session code represents one
         * attendance event.
         */
        Map<String, SessionAnalytics> analytics =
                new LinkedHashMap<>();

        for (Attendance attendance : attendanceList) {

            String sessionCode =
                    attendance.getSessionCode();

            if (sessionCode == null) {
                continue;
            }

            SessionAnalytics data =
                    analytics.get(sessionCode);

            if (data == null) {

                data = new SessionAnalytics();

                data.sessionCode =
                        sessionCode;

                data.course =
                        attendance.getCourse() != null
                                ? attendance.getCourse()
                                : "N/A";

                analytics.put(
                        sessionCode,
                        data
                );
            }

            data.totalStudents++;

            String time =
                    attendance.getCheckInTime();

            if (time != null) {

                if (data.firstAttendance == null
                        || time.compareTo(
                                data.firstAttendance
                        ) < 0) {

                    data.firstAttendance = time;
                }

                if (data.latestAttendance == null
                        || time.compareTo(
                                data.latestAttendance
                        ) > 0) {

                    data.latestAttendance = time;
                }
            }
        }

        response.setContentType(
                "application/json"
        );

        response.setCharacterEncoding(
                "UTF-8"
        );

        PrintWriter out =
                response.getWriter();

        out.print("[");

        boolean first = true;

        for (SessionAnalytics data :
                analytics.values()) {

            if (!first) {
                out.print(",");
            }

            first = false;

            out.print("{");

            out.print("\"sessionCode\":\""
                    + escapeJson(
                            data.sessionCode
                    )
                    + "\",");

            out.print("\"course\":\""
                    + escapeJson(
                            data.course
                    )
                    + "\",");

            out.print("\"totalStudents\":"
                    + data.totalStudents
                    + ",");

            out.print("\"firstAttendance\":\""
                    + escapeJson(
                            data.firstAttendance
                    )
                    + "\",");

            out.print("\"latestAttendance\":\""
                    + escapeJson(
                            data.latestAttendance
                    )
                    + "\"");

            out.print("}");
        }

        out.print("]");
    }

    private String escapeJson(String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "\\r")
                .replace("\n", "\\n");
    }

    private static class SessionAnalytics {

        String sessionCode;
        String course;
        int totalStudents;
        String firstAttendance;
        String latestAttendance;
    }
}