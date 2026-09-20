import model.Lecturer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LecturerLoginServlet")
public class LecturerLoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String lecturerId =
                request.getParameter("lecturerId");

        String password =
                request.getParameter("password");

        LecturerDAO dao = new LecturerDAO();

        Lecturer lecturer =
                dao.login(lecturerId, password);

        if (lecturer != null) {

            HttpSession session =
                    request.getSession(true);

            session.setAttribute(
                    "lecturer",
                    lecturer
            );

            response.sendRedirect(
                    "LecturerDashboardServlet"
            );

        } else {

            request.setAttribute(
                    "error",
                    "Invalid lecturer ID or password."
            );

            request.getRequestDispatcher(
                    "lecturerLogin.jsp"
            ).forward(request, response);
        }
    }
}