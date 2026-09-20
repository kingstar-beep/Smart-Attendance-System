import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/StudentRegisterServlet")
public class StudentRegisterServlet extends HttpServlet {

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String studentId =
                request.getParameter("studentId");

        String name =
                request.getParameter("name");

        String password =
                request.getParameter("password");

        String confirmPassword =
                request.getParameter("confirmPassword");


        // Basic validation
        if (studentId == null
                || name == null
                || password == null
                || confirmPassword == null
                || studentId.trim().isEmpty()
                || name.trim().isEmpty()
                || password.trim().isEmpty()) {

            request.setAttribute(
                    "error",
                    "Please complete all required fields."
            );

            request.getRequestDispatcher(
                    "studentRegister.jsp"
            ).forward(request, response);

            return;
        }


        // Confirm password
        if (!password.equals(confirmPassword)) {

            request.setAttribute(
                    "error",
                    "Passwords do not match."
            );

            request.getRequestDispatcher(
                    "studentRegister.jsp"
            ).forward(request, response);

            return;
        }


        StudentDAO dao =
                new StudentDAO();


        // Check whether student already exists
        if (dao.studentExists(studentId)) {

            request.setAttribute(
                    "error",
                    "A student account with this ID already exists."
            );

            request.getRequestDispatcher(
                    "studentRegister.jsp"
            ).forward(request, response);

            return;
        }


        boolean registered =
                dao.registerStudent(
                        studentId.trim(),
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
                "studentRegister.jsp"
        ).forward(request, response);
    }
}