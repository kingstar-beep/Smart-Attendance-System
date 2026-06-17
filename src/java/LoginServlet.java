import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.Student;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String studentId = request.getParameter("studentId");
        String password = request.getParameter("password");
        
        StudentDAO dao = new StudentDAO();
        Student student = dao.login(studentId, password);
        System.out.println("Student Object = " + student);

        if (student != null) {

            // ✅ CREATE SESSION (IMPORTANT)
            HttpSession session = request.getSession(true);

            // Store student object
            session.setAttribute("student", student);

            // Redirect to QR flow or homepage
            response.sendRedirect("loginSuccess.jsp");

        } else {
            request.setAttribute("error", "Invalid student ID or password");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}