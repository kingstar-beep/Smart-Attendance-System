
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LecturerRegisterServlet")
public class LecturerRegisterServlet extends HttpServlet {

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String lecturerId
                = request.getParameter("lecturerId");

        String name
                = request.getParameter("name");

        String password
                = request.getParameter("password");

        String confirmPassword
                = request.getParameter("confirmPassword");

        if (lecturerId == null
                || name == null
                || password == null
                || confirmPassword == null
                || lecturerId.trim().isEmpty()
                || name.trim().isEmpty()
                || password.trim().isEmpty()) {

            request.setAttribute(
                    "error",
                    "Please complete all required fields."
            );

            request.getRequestDispatcher(
                    "lecturerRegister.jsp"
            ).forward(request, response);

            return;
        }

        if (!password.equals(confirmPassword)) {

            request.setAttribute(
                    "error",
                    "Passwords do not match."
            );

            request.getRequestDispatcher(
                    "lecturerRegister.jsp"
            ).forward(request, response);

            return;
        }

        LecturerDAO dao
                = new LecturerDAO();

        if (dao.lecturerExists(lecturerId)) {

            request.setAttribute(
                    "error",
                    "A lecturer account with this ID already exists."
            );

            request.getRequestDispatcher(
                    "lecturerRegister.jsp"
            ).forward(request, response);

            return;
        }

        boolean registered
                = dao.registerLecturer(
                        lecturerId.trim(),
                        name.trim(),
                        password
                );

        if (registered) {

            request.setAttribute(
                    "success",
                    "Registration successful. You can now log in."
            );

        } else {

            request.setAttribute(
                    "error",
                    "Registration failed. Please try again."
            );
        }

        request.getRequestDispatcher(
                "lecturerRegister.jsp"
        ).forward(request, response);
    }
}
