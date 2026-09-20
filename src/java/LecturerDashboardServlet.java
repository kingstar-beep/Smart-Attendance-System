/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
 /*
import model.Attendance;
import java.io.IOException;
import java.util.List;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/LecturerDashboardServlet")
public class LecturerDashboardServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        AttendanceDAO dao = new AttendanceDAO();

        List<Attendance> attendanceList =
                dao.getAllAttendance();

        request.setAttribute("attendanceList",
                attendanceList);

        request.getRequestDispatcher(
                "lecturerDashboard.jsp")
                .forward(request, response);
    }
} */
 /*
import model.Attendance;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LecturerDashboardServlet")
public class LecturerDashboardServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        // Lecturer must be authenticated
        if (session == null
                || session.getAttribute("lecturer") == null) {

            response.sendRedirect("lecturerLogin.jsp");
            return;
        }

        AttendanceDAO dao =
                new AttendanceDAO();

        List<Attendance> attendanceList =
                dao.getAllAttendance();

        request.setAttribute(
                "attendanceList",
                attendanceList
        );

        request.getRequestDispatcher(
                "lecturerDashboard.jsp"
        ).forward(request, response);
    }
}
 */

import model.Session;
import model.Attendance;
import model.Lecturer;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LecturerDashboardServlet")
public class LecturerDashboardServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session
                = request.getSession(false);

        // Lecturer must be authenticated
        if (session == null
                || session.getAttribute("lecturer") == null) {

            response.sendRedirect(
                    "lecturerLogin.jsp"
            );

            return;
        }

        Lecturer lecturer
                = (Lecturer) session.getAttribute("lecturer");

        String lecturerId
                = lecturer.getLecturerId();

        // Get lecturer's attendance sessions
        SessionDAO sessionDAO
                = new SessionDAO();

        List<Session> sessionList
                = sessionDAO.getSessionsByLecturer(
                        lecturerId
                );

        // Get attendance belonging to lecturer
        AttendanceDAO attendanceDAO
                = new AttendanceDAO();

        List<Attendance> attendanceList
                = attendanceDAO.getAttendanceByLecturer(
                        lecturerId
                );

        request.setAttribute(
                "sessionList",
                sessionList
        );

        request.setAttribute(
                "attendanceList",
                attendanceList
        );

        request.getRequestDispatcher(
                "lecturerDashboard.jsp"
        ).forward(request, response);
    }
}
